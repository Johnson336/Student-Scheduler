package wgu.c196.c196scheduler_niermeyer.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import wgu.c196.c196scheduler_niermeyer.R;
import wgu.c196.c196scheduler_niermeyer.components.Assessment;
import wgu.c196.c196scheduler_niermeyer.components.Course;
import wgu.c196.c196scheduler_niermeyer.components.Term;
import wgu.c196.c196scheduler_niermeyer.database.Repository;

public class MainActivity extends AppCompatActivity {
    public static int numAlert;
    private Repository repo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        repo = new Repository(getApplication());
        Button button = findViewById(R.id.button_Enter);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, TermList.class);
            startActivity(intent);
        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.clearAllData:
                repo.deleteAllAssessments();
                repo.deleteAllCourses();
                repo.deleteAllTerms();
                Toast.makeText(this, "All data cleared.", Toast.LENGTH_LONG).show();
                break;
            case R.id.addSampleData:
                Term term = new Term("Test Term", "2023/01/28", "2023/06/01");
                repo.insert(term);
                Course course = new Course("Algebra II", "2023/01/28", "2023/06/01", "ACTIVE", "No notes available for this course.");
                repo.insert(course);
                Assessment assessment = new Assessment("Final Exam",2,"2023/02/01", "2023/02/01");
                repo.insert(assessment);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}