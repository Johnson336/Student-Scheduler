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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import wgu.c196.c196scheduler_niermeyer.R;
import wgu.c196.c196scheduler_niermeyer.activities.AssessmentList;
import wgu.c196.c196scheduler_niermeyer.activities.CourseList;
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
                    final Course curCourse = mCourses.get(position);
                    /**
                     * Course Data passed into assessment list form
                     */
                    Intent intent = new Intent(context, AssessmentList.class);
                    intent.putExtra("cId", curCourse.getId());
                    intent.putExtra("cTitle", curCourse.getTitle());
                    intent.putExtra("cStartDate", curCourse.getStartDate());
                    intent.putExtra("cEndDate", curCourse.getEndDate());
                    intent.putExtra("cStatus", curCourse.getStatus());
                    intent.putExtra("cNote", curCourse.getNote());
                    intent.putExtra("cInstructorName", curCourse.getInstructorName());
                    intent.putExtra("cInstructorPhone", curCourse.getInstructorPhone());
                    intent.putExtra("cInstructorEmail", curCourse.getInstructorEmail());
                    intent.putExtra("tId", curCourse.getTermID());
                    // intent.putExtra("tId", intent.getStringExtra("tId"));
                    intent.putExtra("tName", intent.getStringExtra("tName"));
                    intent.putExtra("tStart", intent.getStringExtra("tStart"));
                    intent.putExtra("tEnd", intent.getStringExtra("tEnd"));
                    /**
                     * Term Data passed into assessment list form
                     */
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
            holder.bind(cur.toString());
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

    public List<Course> getCoursesFromTermID(int termId) {
        ArrayList<Course> filteredCourses = new ArrayList<>();
        for (Course c : mCourses) {
            if (c.getTermID() == termId) {
                filteredCourses.add(c);
            }
        }
        return filteredCourses;
    }
}
