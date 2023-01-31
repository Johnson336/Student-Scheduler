package wgu.c196.c196scheduler_niermeyer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import wgu.c196.c196scheduler_niermeyer.R;
import wgu.c196.c196scheduler_niermeyer.components.Term;
import wgu.c196.c196scheduler_niermeyer.database.Repository;

public class TermList extends AppCompatActivity {
    private Repository repo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);
        RecyclerView recyclerView = findViewById(R.id.termRecyclerView);
        final TermAdapter termAdapter = new TermAdapter(this);
        recyclerView.setAdapter(termAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        repo = new Repository(getApplication());
        List<Term> allTerms = repo.getAllTerms();
        termAdapter.setTerms(allTerms);

        FloatingActionButton term_list_add = findViewById(R.id.term_list_add_button);
        term_list_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TermList.this, TermDetails.class);
                startActivity(intent);
            }
        });
    }
}