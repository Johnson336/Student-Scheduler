package wgu.c196.c196scheduler_niermeyer.ui;

import static android.content.ContentValues.TAG;

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

import wgu.c196.c196scheduler_niermeyer.R;
import wgu.c196.c196scheduler_niermeyer.activities.AssessmentList;
import wgu.c196.c196scheduler_niermeyer.components.Course;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    private List<Course> mCourses;
    private final Context context;
    private final LayoutInflater mInflater;

    class CourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseItemView;
        private final Button courseDetailButton;
        // private final Button courseDeleteButton;

        public CourseViewHolder(View itemView) {
            super(itemView);
            courseItemView = itemView.findViewById(R.id.course_list_item);
            courseDetailButton = itemView.findViewById(R.id.course_item_info_graphic);
            // courseDeleteButton = itemView.findViewById(R.id.course_item_delete_graphic);

            courseDetailButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Course cur = mCourses.get(position);
                    /**
                     *  Update Course Data in Preferences
                     */
                    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor prefEdit = pref.edit();
                    prefEdit.putInt("courseId", cur.getId());
                    prefEdit.putString("courseTitle", cur.getTitle());
                    prefEdit.putString("courseStart", cur.getStartDate());
                    prefEdit.putString("courseEnd", cur.getEndDate());
                    prefEdit.putString("courseStatus", cur.getStatus());
                    prefEdit.putString("courseNote", cur.getNote());
                    prefEdit.putString("courseInstName", cur.getInstructorName());
                    prefEdit.putString("courseInstPhone", cur.getInstructorPhone());
                    prefEdit.putString("courseInstEmail", cur.getInstructorEmail());
                    prefEdit.apply();
                    Intent intent = new Intent(context, AssessmentList.class);
                    context.startActivity(intent);
                }
            });

            /*
            courseDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Course cur = mCourses.get(getAdapterPosition());
                    mCourses.remove(cur);
                    notifyDataSetChanged();
                }
            });

             */
        }

        public void bind(String text) {
            courseItemView.setText(text);
        }
    }
    public CourseAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    // Create new views (invoked by the layout manager
    @NonNull
    @Override
    public CourseAdapter.CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view holder, which defines the UI of the list item
        View view = mInflater.inflate(R.layout.course_list_item, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.CourseViewHolder holder, int position) {
        if (mCourses != null) {
            Course cur = mCourses.get(position);
            holder.bind(cur.getTitle());
        } else {
            holder.bind("No courses found.");
        }
    }

    @Override
    public int getItemCount() {
        if (mCourses != null) {
            return mCourses.size();
        } else {
            return 0;
        }
    }

    public void setCourses(List<Course> courses) {
        mCourses = courses;
        notifyDataSetChanged();
    }

    public Course getCourseAtPosition(int position) {
        return mCourses.get(position);
    }

}
