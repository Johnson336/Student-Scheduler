package wgu.c196.c196scheduler_niermeyer.activities;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
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
import android.os.Bundle;
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
    private int cTermId;
    private String cTermName;
    private String cTermStart;
    private String cTermEnd;
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

        mCourseViewModel.getAllCourses().observe(this, courseAdapter::setCourses);

        LiveData<List<Course>> allCourses = repo.getAllCourses();
        courseAdapter.setCourses(allCourses.getValue());

        int termId = getIntent().getIntExtra("tId", 0);
        LiveData<List<Course>> filteredCourses = repo.getCoursesByTermID(termId);

        if (filteredCourses != null) {
            courseAdapter.setCourses(filteredCourses.getValue());
        }


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

                        // Delete the term
                        mCourseViewModel.delete(thisCourse);
                    }
                });
        helper.attachToRecyclerView(recyclerView);

        termName = findViewById(R.id.editText_term_detail_name);
        termStart = findViewById(R.id.editText_term_detail_start);
        termEnd = findViewById(R.id.editText_term_detail_end);
        termSave = findViewById(R.id.button_term_save);
        // Term ID passed in from TermList form
        // Or from savedInstanceState if we pressed back
        /*
        if (savedInstanceState != null) {
            Toast.makeText(this, "Loading instance.", Toast.LENGTH_LONG).show();
            cTermId = savedInstanceState.getInt("tId");
            cTermName = savedInstanceState.getString("tName");
            cTermStart = savedInstanceState.getString("tStartDate");
            cTermEnd = savedInstanceState.getString("tEndDate");
            termName.setText(cTermName);
            termStart.setText(cTermStart);
            termEnd.setText(cTermEnd);
        } else {

         */
        cTermId = getIntent().getIntExtra("tId", 0);
        cTermName = getIntent().getStringExtra("tName");
        cTermStart = getIntent().getStringExtra("tStartDate");
        cTermEnd = getIntent().getStringExtra("tEndDate");
        termName.setText((cTermName != null ? cTermName : ""));
        termStart.setText((cTermStart != null ? cTermStart : ""));
        termEnd.setText((cTermEnd != null ? cTermEnd : ""));
        System.err.println("Term ID passed in: " + cTermId);
        // }


        termStart.setOnClickListener(v -> {
            String info = termStart.getText().toString();
            if (info.equals("")) info = "2023/01/01";
            try {
                calStart.setTime(sDateFormat.parse(info));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            new DatePickerDialog(CourseList.this, termStartDate, calStart.get(Calendar.YEAR), calStart.get(Calendar.MONTH), calStart.get(Calendar.DAY_OF_MONTH)).show();
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
            new DatePickerDialog(CourseList.this, termEndDate, calEnd.get(Calendar.YEAR), calEnd.get(Calendar.MONTH), calEnd.get(Calendar.DAY_OF_MONTH)).show();
        });
        termEndDate = (view, year, monthOfYear, dayOfMonth) -> {
            calEnd.set(Calendar.YEAR, year);
            calEnd.set(Calendar.MONTH, monthOfYear);
            calEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            termEnd.setText(sDateFormat.format(calEnd.getTime()));
        };

        termSave.setOnClickListener(v -> {
            Term newTerm = new Term(termName.getText().toString(), termStart.getText().toString(), termEnd.getText().toString());
            if (cTermId != 0) {
                newTerm.setId(cTermId);
                repo.update(newTerm);
            } else {
                repo.insert(newTerm);
            }
            Toast.makeText(getApplicationContext(), String.format("Term has been %s.", (cTermId == 0) ? "added" : "updated"), Toast.LENGTH_LONG).show();
        });


        ExtendedFloatingActionButton button_course_add = findViewById(R.id.button_addCourse);
        button_course_add.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Enter Course Title");

            // Set up the course title input
            final EditText input = new EditText(this);
            // Specify the type of input expected
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);

            // Set up the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Course newCourse = new Course(input.getText().toString(), "", "", "", "");
                    newCourse.setTermID(cTermId);
                    repo.insert(newCourse);
                }
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
            builder.show();

        });
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("tId", cTermId);
        outState.putString("tName", cTermName);
        outState.putString("tStart", cTermStart);
        outState.putString("tEnd", cTermEnd);
        super.onSaveInstanceState(outState);
    }



    /**
    @Override
    protected void onResume() {
        super.onResume();
        LiveData<List<Course>> allCourses = repo.getAllCourses();
        RecyclerView recyclerView = findViewById(R.id.recyclerView_courseList);
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        courseAdapter.setCourses(allCourses.getValue());
    }
    */
}