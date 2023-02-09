package wgu.c196.c196scheduler_niermeyer.activities;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import wgu.c196.c196scheduler_niermeyer.R;
import wgu.c196.c196scheduler_niermeyer.components.Assessment;
import wgu.c196.c196scheduler_niermeyer.components.Course;
import wgu.c196.c196scheduler_niermeyer.components.Term;
import wgu.c196.c196scheduler_niermeyer.database.Repository;
import wgu.c196.c196scheduler_niermeyer.models.AssessmentViewModel;
import wgu.c196.c196scheduler_niermeyer.models.CourseViewModel;
import wgu.c196.c196scheduler_niermeyer.ui.AssessmentAdapter;
import wgu.c196.c196scheduler_niermeyer.ui.CourseAdapter;

public class AssessmentList extends AppCompatActivity {
    private EditText courseTitle;
    private EditText courseStart;
    private DatePickerDialog.OnDateSetListener courseStartDate;
    private final Calendar calStart = Calendar.getInstance();
    private EditText courseEnd;
    private DatePickerDialog.OnDateSetListener courseEndDate;
    private final Calendar calEnd = Calendar.getInstance();
    private String dateFormat = "yyyy/MM/dd";
    private SimpleDateFormat sDateFormat = new SimpleDateFormat(dateFormat, Locale.US);
    private EditText courseStatus;
    private EditText courseNote;
    private EditText instructorName;
    private EditText instructorPhone;
    private EditText instructorEmail;
    private Button courseSave;
    private AssessmentViewModel mAssessmentViewModel;
    private RecyclerView recyclerView;

    private Repository repo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_list);

        // Get our repo
        repo = new Repository(getApplication());

        recyclerView = findViewById(R.id.recyclerView_assessmentList);
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAssessmentViewModel = new ViewModelProvider(this).get(AssessmentViewModel.class);

        /**
         * Retrieve Course data from preferences
         */
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int cId = pref.getInt("courseId", 0);
        String cTitle = pref.getString("courseTitle", null);
        String cStart = pref.getString("courseStart", null);
        String cEnd = pref.getString("courseEnd", null);
        String cStatus = pref.getString("courseStatus", null);
        String cNote = pref.getString("courseNote", null);
        String cInstName = pref.getString("courseInstName", null);
        String cInstPhone = pref.getString("courseInstPhone", null);
        String cInstEmail = pref.getString("courseInstEmail", null);
        int tId = pref.getInt("termId", 0);

        List<Assessment> allAssessments = repo.getAllAssessments();
        // assessmentAdapter.setAssessments(allAssessments);

        /**
         * ArrayList Used to filter assessments by CourseID
         */

        ArrayList<Assessment> filteredAssessments = new ArrayList<>();
        for (Assessment a : allAssessments) {
            if (a.getCourseID() == cId) {
                filteredAssessments.add(a);
            }
        }
        assessmentAdapter.setAssessments(filteredAssessments);

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
                        Assessment thisAssessment = assessmentAdapter.getAssessmentAtPosition(position);
                        Toast.makeText(AssessmentList.this, "Deleting " +
                                thisAssessment.getTitle(), Toast.LENGTH_LONG).show();

                        // Delete the assessment
                        repo.delete(thisAssessment);
                        List<Assessment> filteredAssessments = repo.getAssessmentsByCourseID(cId);
                        assessmentAdapter.setAssessments(filteredAssessments);
                        assessmentAdapter.notifyItemInserted(filteredAssessments.size());
                    }
                });
        helper.attachToRecyclerView(recyclerView);

        courseTitle = findViewById(R.id.editText_course_detail_title);
        courseStart = findViewById(R.id.editText_course_detail_start);
        courseEnd = findViewById(R.id.editText_course_detail_end);
        courseStatus = findViewById(R.id.editText_course_detail_status);
        courseNote = findViewById(R.id.editText_course_detail_note);
        instructorName = findViewById(R.id.editText_course_instructor_name);
        instructorPhone = findViewById(R.id.editText_course_instructor_phone);
        instructorEmail = findViewById(R.id.editText_course_instructor_email);
        courseSave = findViewById(R.id.button_course_save);

        courseTitle.setText((cTitle!=null? cTitle : ""));
        courseStart.setText((cStart!=null ? cStart : ""));
        courseEnd.setText((cEnd!=null ? cEnd : ""));
        courseStatus.setText((cStatus!=null ? cStatus : ""));
        courseNote.setText((cNote!=null ? cNote : ""));
        instructorName.setText((cInstName!=null ? cInstName : ""));
        instructorPhone.setText((cInstPhone!=null ? cInstPhone : ""));
        instructorEmail.setText((cInstEmail!=null ? cInstEmail : ""));

        /*
         * Course Start Date datePicker callback
         */
        courseStart.setOnClickListener(v -> {
            String info = courseStart.getText().toString();
            if (info.equals("")) info = "2023/01/01";
            try {
                calStart.setTime(sDateFormat.parse(info));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            new DatePickerDialog(AssessmentList.this, R.style.DialogTheme, courseStartDate, calStart.get(Calendar.YEAR), calStart.get(Calendar.MONTH), calStart.get(Calendar.DAY_OF_MONTH)).show();
        });
        courseStartDate = (view, year, monthOfYear, dayOfMonth) -> {
            calStart.set(Calendar.YEAR, year);
            calStart.set(Calendar.MONTH, monthOfYear);
            calStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            courseStart.setText(sDateFormat.format(calStart.getTime()));
        };
        /*
         * Course End Date datePicker callback
         */
        courseEnd.setOnClickListener(v -> {
            String info = courseEnd.getText().toString();
            if (info.equals("")) info = "2023/01/01";
            try {
                calEnd.setTime(sDateFormat.parse(info));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            new DatePickerDialog(AssessmentList.this, R.style.DialogTheme, courseEndDate, calEnd.get(Calendar.YEAR), calEnd.get(Calendar.MONTH), calEnd.get(Calendar.DAY_OF_MONTH)).show();
        });
        courseEndDate = (view, year, monthOfYear, dayOfMonth) -> {
            calEnd.set(Calendar.YEAR, year);
            calEnd.set(Calendar.MONTH, monthOfYear);
            calEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            courseEnd.setText(sDateFormat.format(calEnd.getTime()));
        };
        /*
         * Save Course Button callback
         */
        courseSave.setOnClickListener(v -> {
            Course course = new Course(
                    courseTitle.getText().toString(),
                    courseStart.getText().toString(),
                    courseEnd.getText().toString(),
                    courseStatus.getText().toString(),
                    courseNote.getText().toString());
            if (!instructorName.getText().toString().isEmpty()) {
                course.setInstructorName(instructorName.getText().toString());
            }
            if (!instructorPhone.getText().toString().isEmpty()) {
                course.setInstructorPhone(instructorPhone.getText().toString());
            }
            if (!instructorEmail.getText().toString().isEmpty()) {
                course.setInstructorEmail(instructorEmail.getText().toString());
            }
            course.setTermID(tId);
            if (cId != 0) {
                course.setId(cId);
                repo.update(course);
            } else {
                repo.insert(course);
            }
            Toast.makeText(getApplicationContext(), String.format("Course has been %s.", (cId == 0) ? "added" : "updated"), Toast.LENGTH_LONG).show();
        });


        ExtendedFloatingActionButton button_assessment_add = findViewById(R.id.button_addAssessment);
        button_assessment_add.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, android.R.style.Widget_Material_Light_PopupWindow));
            builder.setTitle("Enter Assessment Title");

            builder.setIcon(R.drawable.ic_launcher_foreground);

            // Set up the course title input
            final EditText input = new EditText(this);
            input.setHint("New Assessment Title");
            // Specify the type of input expected
            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
            builder.setView(input);

            // Set up the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Assessment newAssessment = new Assessment(input.getText().toString(), 0,"", "");
                    newAssessment.setCourseID(cId);
                    repo.insert(newAssessment);
                    List<Assessment> filteredAssessments = repo.getAssessmentsByCourseID(cId);
                    assessmentAdapter.setAssessments(filteredAssessments);
                    assessmentAdapter.notifyItemInserted(filteredAssessments.size());
                }
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
            builder.show();
        });
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_assessment, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, courseNote.getText().toString());
                sendIntent.putExtra(Intent.EXTRA_TITLE, "C196 Notification");
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
                return true;
            case R.id.notifyStart:

                String date = courseStart.getText().toString();
                Date newDate = null;
                try {
                    newDate = sDateFormat.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Unable to add notification, invalid date.", Toast.LENGTH_LONG).show();
                    break;
                }
                Long trigger = newDate.getTime();
                Intent intent = new Intent(AssessmentList.this, NotificationReceiver.class);
                intent.putExtra("key", String.format(Locale.US, "%s starts on %s!", courseTitle.getText().toString(),courseStart.getText().toString()));
                PendingIntent sender = PendingIntent.getBroadcast(AssessmentList.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                manager.set(AlarmManager.RTC_WAKEUP, trigger, sender);

                return true;
            case R.id.notifyEnd:
                date = courseEnd.getText().toString();
                newDate = null;
                try {
                    newDate = sDateFormat.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Unable to add notification, invalid date.", Toast.LENGTH_LONG).show();
                    break;
                }
                trigger = newDate.getTime();
                intent = new Intent(AssessmentList.this, NotificationReceiver.class);
                intent.putExtra("key", String.format(Locale.US, "%s ends on %s!", courseTitle.getText().toString(),courseEnd.getText().toString()));
                sender = PendingIntent.getBroadcast(AssessmentList.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
                manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                manager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
