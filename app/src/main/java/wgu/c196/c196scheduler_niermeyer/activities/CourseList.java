package wgu.c196.c196scheduler_niermeyer.activities;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import wgu.c196.c196scheduler_niermeyer.R;
import wgu.c196.c196scheduler_niermeyer.components.Course;
import wgu.c196.c196scheduler_niermeyer.components.Term;
import wgu.c196.c196scheduler_niermeyer.database.Repository;
import wgu.c196.c196scheduler_niermeyer.models.CourseViewModel;
import wgu.c196.c196scheduler_niermeyer.models.TermViewModel;
import wgu.c196.c196scheduler_niermeyer.ui.CourseAdapter;
import wgu.c196.c196scheduler_niermeyer.ui.TermAdapter;

public class CourseList extends AppCompatActivity {
    private EditText termName;
    private EditText termStart;
    private DatePickerDialog.OnDateSetListener termStartDate;
    private final Calendar calStart = Calendar.getInstance();
    private EditText termEnd;
    private DatePickerDialog.OnDateSetListener termEndDate;
    private final Calendar calEnd = Calendar.getInstance();
    private String dateFormat = "yyyy/MM/dd";
    private SimpleDateFormat sDateFormat = new SimpleDateFormat(dateFormat, Locale.US);
    private Button termSave;
    private CourseViewModel mCourseViewModel;
    private RecyclerView recyclerView;
    private Repository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        // Get our repo
        repo = new Repository(getApplication());

        recyclerView = findViewById(R.id.recyclerView_courseList);
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mCourseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);

        /**
         * Retrieve Course data from preferences
         */
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int tId = pref.getInt("termId", 0);
        String tName = pref.getString("termName", null);
        String tStart = pref.getString("termStart", null);
        String tEnd = pref.getString("termEnd", null);

        List<Course> allCourses = repo.getAllCourses();
        /*
        ArrayList<Course> filteredCourses = new ArrayList<>();
        for (Course c : allCourses) {
            if (c.getTermID() == tId) {
                filteredCourses.add(c);
            }
        }
        courseAdapter.setCourses(filteredCourses);
         */
        List<Course> filteredCourses = repo.getCoursesByTermID(tId);
        courseAdapter.setCourses(filteredCourses);

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
                        Course thisCourse = courseAdapter.getCourseAtPosition(position);
                        Toast.makeText(CourseList.this, "Deleting " +
                                thisCourse.getTitle(), Toast.LENGTH_LONG).show();

                        // Delete the course
                        repo.delete(thisCourse);
                        List<Course> filteredCourses = repo.getCoursesByTermID(tId);
                        courseAdapter.setCourses(filteredCourses);
                        courseAdapter.notifyItemInserted(filteredCourses.size());
                    }
                });
        helper.attachToRecyclerView(recyclerView);

        termName = findViewById(R.id.editText_term_detail_name);
        termStart = findViewById(R.id.editText_term_detail_start);
        termEnd = findViewById(R.id.editText_term_detail_end);
        termSave = findViewById(R.id.button_term_save);

        termName.setText((tName != null ? tName : ""));
        termStart.setText((tStart != null ? tStart : ""));
        termEnd.setText((tEnd != null ? tEnd : ""));



        termStart.setOnClickListener(v -> {
            String info = termStart.getText().toString();
            if (info.equals("")) info = "2023/01/01";
            try {
                calStart.setTime(sDateFormat.parse(info));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            new DatePickerDialog(CourseList.this, R.style.DialogTheme, termStartDate, calStart.get(Calendar.YEAR), calStart.get(Calendar.MONTH), calStart.get(Calendar.DAY_OF_MONTH)).show();
        });
        termStartDate = (view, year, monthOfYear, dayOfMonth) -> {
            calStart.set(Calendar.YEAR, year);
            calStart.set(Calendar.MONTH, monthOfYear);
            calStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            termStart.setText(sDateFormat.format(calStart.getTime()));
        };
        termEnd.setOnClickListener(v -> {
            String info = termEnd.getText().toString();
            if (info.equals("")) info = "2023/01/01";
            try {
                calEnd.setTime(sDateFormat.parse(info));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            new DatePickerDialog(CourseList.this, R.style.DialogTheme, termEndDate, calEnd.get(Calendar.YEAR), calEnd.get(Calendar.MONTH), calEnd.get(Calendar.DAY_OF_MONTH)).show();
        });
        termEndDate = (view, year, monthOfYear, dayOfMonth) -> {
            calEnd.set(Calendar.YEAR, year);
            calEnd.set(Calendar.MONTH, monthOfYear);
            calEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            termEnd.setText(sDateFormat.format(calEnd.getTime()));
        };

        termSave.setOnClickListener(v -> {
            Term newTerm = new Term(termName.getText().toString(), termStart.getText().toString(), termEnd.getText().toString());
            if (tId != 0) {
                newTerm.setId(tId);
                repo.update(newTerm);
            } else {
                repo.insert(newTerm);
            }
            Toast.makeText(getApplicationContext(), String.format("Term has been %s.", (tId == 0) ? "added" : "updated"), Toast.LENGTH_LONG).show();
        });


        ExtendedFloatingActionButton button_course_add = findViewById(R.id.button_addCourse);
        button_course_add.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, android.R.style.Widget_Material_Light_PopupWindow));
            builder.setTitle("Enter Course Title");
            builder.setIcon(R.drawable.ic_launcher_foreground);

            // Set up the course title input
            final EditText input = new EditText(this);
            input.setHint("New Course Title");
            // Specify the type of input expected
            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
            builder.setView(input);

            // Set up the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Course newCourse = new Course(input.getText().toString(), "", "", "", "");
                    newCourse.setTermID(tId);
                    repo.insert(newCourse);
                    List<Course> filteredCourses = repo.getCoursesByTermID(tId);
                    courseAdapter.setCourses(filteredCourses);
                    courseAdapter.notifyItemInserted(filteredCourses.size());
                }
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
            builder.show();

        });
    }
}