package wgu.c196.c196scheduler_niermeyer.activities;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import wgu.c196.c196scheduler_niermeyer.R;
import wgu.c196.c196scheduler_niermeyer.components.Assessment;
import wgu.c196.c196scheduler_niermeyer.database.Repository;

public class AssessmentDetails extends AppCompatActivity {
    private EditText assessmentTitle;
    private EditText assessmentStart;
    private DatePickerDialog.OnDateSetListener assessmentStartDate;
    private final Calendar calStart = Calendar.getInstance();
    private EditText assessmentEnd;
    private DatePickerDialog.OnDateSetListener assessmentEndDate;
    private final Calendar calEnd = Calendar.getInstance();
    private String dateFormat = "yyyy/MM/dd";
    private SimpleDateFormat sDateFormat = new SimpleDateFormat(dateFormat, Locale.US);
    private Button assessmentSave;
    private Repository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);

        // Get our repo
        repo = new Repository(getApplication());

        assessmentTitle = findViewById(R.id.editText_assessment_detail_title);
        assessmentStart = findViewById(R.id.editText_assessment_detail_start);
        assessmentEnd = findViewById(R.id.editText_assessment_detail_end);
        assessmentSave = findViewById(R.id.button_assessment_save);

        /**
         * Retrieve Assessment data from preferences
         */
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int aId = pref.getInt("assessmentId", 0);
        String aTitle = pref.getString("assessmentTitle", null);
        String aStart = pref.getString("assessmentStart", null);
        String aEnd = pref.getString("assessmentEnd", null);
        int cId = pref.getInt("courseId", 0);

        assessmentTitle.setText((aTitle!=null ? aTitle : ""));
        assessmentStart.setText((aStart!=null ? aStart : ""));
        assessmentEnd.setText((aEnd!=null ? aEnd : ""));

        assessmentStart.setOnClickListener(v-> {
            String info = assessmentStart.getText().toString();
            if (info.equals("")) info = "2023/01/01";
            try {
                calStart.setTime(sDateFormat.parse(info));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            new DatePickerDialog(AssessmentDetails.this, assessmentStartDate, calStart.get(Calendar.YEAR), calStart.get(Calendar.MONTH), calStart.get(Calendar.DAY_OF_MONTH)).show();
            });
        assessmentStartDate = (view, year, monthOfYear, dayOfMonth) -> {
            calStart.set(Calendar.YEAR, year);
            calStart.set(Calendar.MONTH, monthOfYear);
            calStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            assessmentStart.setText(sDateFormat.format(calStart.getTime()));
        };
        assessmentEnd.setOnClickListener(v -> {
            String info = assessmentEnd.getText().toString();
            if (info.equals("")) info = "2023/01/01";
            try {
                calEnd.setTime(sDateFormat.parse(info));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            new DatePickerDialog(AssessmentDetails.this, assessmentEndDate, calEnd.get(Calendar.YEAR), calEnd.get(Calendar.MONTH), calEnd.get(Calendar.DAY_OF_MONTH)).show();
        });
        assessmentEndDate = (view, year, monthOfYear, dayOfMonth) -> {
            calEnd.set(Calendar.YEAR, year);
            calEnd.set(Calendar.MONTH, monthOfYear);
            calEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            assessmentEnd.setText(sDateFormat.format(calEnd.getTime()));
        };

        assessmentSave.setOnClickListener(v -> {
            Assessment newAssessment = new Assessment(assessmentTitle.getText().toString(), assessmentStart.getText().toString(), assessmentEnd.getText().toString());
            newAssessment.setCourseID(cId);
            if (aId != 0) {
                newAssessment.setId(aId);
                repo.update(newAssessment);
            } else {
                repo.insert(newAssessment);
            }
            Toast.makeText(getApplicationContext(), String.format("Assessment has been %s.", (aId == 0) ? "added" : "updated"), Toast.LENGTH_LONG).show();
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_assessment_details, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.notifyStart:
                String date = assessmentStart.getText().toString();
                Date newDate = null;
                try {
                    newDate = sDateFormat.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Unable to add notification, invalid date.", Toast.LENGTH_LONG).show();
                    break;
                }
                Long trigger = newDate.getTime();
                Intent intent = new Intent(AssessmentDetails.this, NotificationReceiver.class);
                intent.putExtra("key", String.format(Locale.US, "%s ends on %s!", assessmentTitle.getText().toString(),assessmentStart.getText().toString()));
                PendingIntent sender = PendingIntent.getBroadcast(AssessmentDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                manager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
                return true;
            case R.id.notifyEnd:
                date = assessmentEnd.getText().toString();
                newDate = null;
                try {
                    newDate = sDateFormat.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Unable to add notification, invalid date.", Toast.LENGTH_LONG).show();
                    break;
                }
                trigger = newDate.getTime();
                intent = new Intent(AssessmentDetails.this, NotificationReceiver.class);
                intent.putExtra("key", String.format(Locale.US, "%s ends on %s!", assessmentTitle.getText().toString(),assessmentEnd.getText().toString()));
                sender = PendingIntent.getBroadcast(AssessmentDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
                manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                manager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
