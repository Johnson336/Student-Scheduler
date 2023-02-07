package wgu.c196.c196scheduler_niermeyer.models;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import java.util.List;

import wgu.c196.c196scheduler_niermeyer.components.Course;
import wgu.c196.c196scheduler_niermeyer.database.Repository;

public class CourseViewModel extends AndroidViewModel {
    private final Repository mRepository;
    private final List<Course> mAllCourses;


    public CourseViewModel(Application application) {
        super(application);
        mRepository = new Repository(application);
        mAllCourses = mRepository.getAllCourses();

    }

    public void insert(Course course) { mRepository.insert(course); }
    public void update(Course course) { mRepository.update(course); }
    public List<Course> getAllCourses() { return mAllCourses; }
    public List<Course> getCoursesByTermID(int id) { return mRepository.getCoursesByTermID(id); }
    public Course getCourseByID(int i) { return mRepository.getCourseByID(i); }
    public void delete(Course course) { mRepository.delete(course); }
    public void deleteAll() { mRepository.deleteAllCourses(); }

}
