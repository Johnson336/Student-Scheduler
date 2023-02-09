package wgu.c196.c196scheduler_niermeyer.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

import wgu.c196.c196scheduler_niermeyer.R;
import wgu.c196.c196scheduler_niermeyer.activities.AssessmentDetails;
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
                     *  Update Assessment Data in Preferences
                     */
                    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor prefEdit = pref.edit();
                    prefEdit.putInt("assessmentId", cur.getId());
                    prefEdit.putString("assessmentTitle", cur.getTitle());
                    prefEdit.putInt("assessmentType", cur.getType());
                    prefEdit.putString("assessmentStart", cur.getStartDate());
                    prefEdit.putString("assessmentEnd", cur.getEndDate());
                    prefEdit.apply();
                    Intent intent = new Intent(context, AssessmentDetails.class);
                    context.startActivity(intent);
                }
            });
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
