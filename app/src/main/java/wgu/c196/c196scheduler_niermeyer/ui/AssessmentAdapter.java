package wgu.c196.c196scheduler_niermeyer.ui;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import wgu.c196.c196scheduler_niermeyer.R;
import wgu.c196.c196scheduler_niermeyer.activities.AssessmentDetails;
import wgu.c196.c196scheduler_niermeyer.activities.AssessmentList;
import wgu.c196.c196scheduler_niermeyer.components.Assessment;
import wgu.c196.c196scheduler_niermeyer.components.Course;
import wgu.c196.c196scheduler_niermeyer.components.Term;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {

    private List<Assessment> mAssessments;
    private Course mCourse;
    private Term mTerm;
    private final Context context;
    private final LayoutInflater mInflater;

    class AssessmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView assessmentItemView;
        private final Button assessmentDetailButton;
        // private final Button assessmentDeleteButton;

        public AssessmentViewHolder(View itemView) {
            super(itemView);
            assessmentItemView = itemView.findViewById(R.id.assessment_list_item);
            assessmentDetailButton = itemView.findViewById(R.id.assessment_item_info_graphic);
            // assessmentDeleteButton = itemView.findViewById(R.id.assessment_item_delete_graphic);

            assessmentDetailButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Assessment cur = mAssessments.get(position);
                    /**
                     * Assessment Data passed into assessment detail form
                     */
                    Intent intent = new Intent(context, AssessmentDetails.class);
                    intent.putExtra("aId", cur.getId());
                    intent.putExtra("aTitle", cur.getTitle());
                    intent.putExtra("aStartDate", cur.getStartDate());
                    intent.putExtra("aEndDate", cur.getEndDate());
                    // forward on course data
                    intent.putExtra("cId", cur.getCourseID());
                    intent.putExtra("cTitle", intent.getStringExtra("cTitle"));
                    intent.putExtra("cStart", intent.getStringExtra("cStart"));
                    intent.putExtra("cEnd", intent.getStringExtra("cEnd"));
                    intent.putExtra("cStatus", intent.getStringExtra("cStatus"));
                    intent.putExtra("cNote", intent.getStringExtra("cNote"));
                    intent.putExtra("cInstructorName", intent.getStringExtra("cInstructorName"));
                    intent.putExtra("cInstructorPhone", intent.getStringExtra("cInstructorPhone"));
                    intent.putExtra("cInstructorEmail", intent.getStringExtra("cInstructorEmail"));
                    // forward on term data
                    intent.putExtra("tId", intent.getIntExtra("tId", 0));
                    intent.putExtra("tName", intent.getStringExtra("tName"));
                    intent.putExtra("tStart", intent.getStringExtra("tStart"));
                    intent.putExtra("tEnd", intent.getStringExtra("tEnd"));



                    context.startActivity(intent);

                }
            });

            /*
            assessmentDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Assessment cur = mAssessments.get(getAdapterPosition());
                    mAssessments.remove(cur);
                    notifyDataSetChanged();
                }
            });

             */
        }

        public void bind(String text) {
            assessmentItemView.setText(text);
        }
    }
    public AssessmentAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    // Create new views (invoked by the layout manager
    @NonNull
    @Override
    public AssessmentAdapter.AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view holder, which defines the UI of the list item
        View view = mInflater.inflate(R.layout.assessment_list_item, parent, false);
        return new AssessmentAdapter.AssessmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentAdapter.AssessmentViewHolder holder, int position) {
        if (mAssessments != null) {
            Assessment cur = mAssessments.get(position);
            holder.bind(cur.getTitle());
        } else {
            holder.bind("No assessments found.");
        }
    }

    @Override
    public int getItemCount() {
        if (mAssessments != null) {
            return mAssessments.size();
        } else {
            return 0;
        }
    }

    public void setAssessments(List<Assessment> assessments) {
        mAssessments = assessments;
        notifyDataSetChanged();
    }

    public Assessment getAssessmentAtPosition(int position) {
        return mAssessments.get(position);
    }
}
