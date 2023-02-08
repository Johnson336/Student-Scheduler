package wgu.c196.c196scheduler_niermeyer.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import wgu.c196.c196scheduler_niermeyer.R;
import wgu.c196.c196scheduler_niermeyer.components.Assessment;
import wgu.c196.c196scheduler_niermeyer.components.Course;
import wgu.c196.c196scheduler_niermeyer.components.Term;
import wgu.c196.c196scheduler_niermeyer.database.Repository;
import wgu.c196.c196scheduler_niermeyer.models.TermViewModel;
import wgu.c196.c196scheduler_niermeyer.ui.TermAdapter;

public class TermList extends AppCompatActivity {
    public static final int NEW_TERM_ACTIVITY_REQUEST_CODE = 1;
    private TermViewModel mTermViewModel;
    private RecyclerView recyclerView;
    private Repository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);

        // Get our Repository
        repo = new Repository(getApplication());

        recyclerView = findViewById(R.id.recyclerView_courseList);
        final TermAdapter termAdapter = new TermAdapter(this);
        recyclerView.setAdapter(termAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mTermViewModel = new ViewModelProvider(this).get(TermViewModel.class);

        mTermViewModel.getAllTerms().observe(this, termAdapter::setTerms);

        LiveData<List<Term>> allTerms = repo.getAllTerms();
        termAdapter.setTerms(allTerms.getValue());

        // Add functionality to swipe items in the recyclerview to delete that item
        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder,
                                          int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Term thisTerm = termAdapter.getTermAtPosition(position);
                        ArrayList<Course> termCourses = new ArrayList<>(repo.getCoursesByTermID(thisTerm.getId()));
                        if (termCourses.size() != 0) {
                            Toast.makeText(TermList.this, String.format(Locale.US,
                                    "Unable to delete %s, %d courses are assigned.",
                                    thisTerm.getName(),
                                    termCourses.size()), Toast.LENGTH_LONG).show();
                            termAdapter.notifyItemChanged(position);
                        } else {
                            Toast.makeText(TermList.this, "Deleting " +
                                    thisTerm.getName(), Toast.LENGTH_LONG).show();

                            // Delete the term
                            mTermViewModel.delete(thisTerm);
                        }
                    }
                });
        helper.attachToRecyclerView(recyclerView);

        ExtendedFloatingActionButton button_term_add = findViewById(R.id.button_addTerm);
        button_term_add.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, android.R.style.Widget_Material_Light_PopupWindow));
            builder.setTitle("Enter Term Name");
            builder.setIcon(R.drawable.ic_launcher_foreground);

            // Set up the course title input
            final EditText input = new EditText(this);
            input.setHint("New Term Name");
            // Specify the type of input expected
            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
            builder.setView(input);

            // Set up the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Term newTerm = new Term(input.getText().toString(), "", "");
                    repo.insert(newTerm);
                }
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
            builder.show();
        });
    }

}