package wgu.c196.c196scheduler_niermeyer.database;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.room.Query;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import wgu.c196.c196scheduler_niermeyer.components.Assessment;
import wgu.c196.c196scheduler_niermeyer.components.Course;
import wgu.c196.c196scheduler_niermeyer.components.Term;
import wgu.c196.c196scheduler_niermeyer.dao.AssessmentDAO;
import wgu.c196.c196scheduler_niermeyer.dao.CourseDAO;
import wgu.c196.c196scheduler_niermeyer.dao.TermDAO;

public class Repository {
    private AssessmentDAO mAssessmentDAO;
    private CourseDAO mCourseDAO;
    private TermDAO mTermDAO;
    private List<Assessment> mAllAssessments;
    private List<Course> mAllCourses;
    private LiveData<List<Term>> mAllTerms;
    private Term selectedTerm;
    private List<Course> filteredCourses;
    private Course selectedCourse;
    private List<Assessment> filteredAssessments;
    private Assessment selectedAssessment;
    private final int dbDelay = 50;

    public Repository(Application application) {
        DatabaseBuilder db = DatabaseBuilder.getDatabase(application);
        mAssessmentDAO = db.assessmentDAO();
        mCourseDAO = db.courseDAO();
        mTermDAO = db.termDAO();
        mAllAssessments = mAssessmentDAO.getAllAssessments();
        mAllCourses = mCourseDAO.getAllCourses();
        mAllTerms = mTermDAO.getAllTerms();
    }
    private void dbDelay() {
        try {
            Thread.sleep(dbDelay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void dbDelay(int delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Terms
     */
    public LiveData<List<Term>> getAllTerms() {
        return mAllTerms;
    }
    public void insert(Term term) {
        DatabaseBuilder.databaseExecutor.execute(()->{
            mTermDAO.insert(term);
        });
        dbDelay();
    }
    public Term getTermByID(int i) {
        DatabaseBuilder.databaseExecutor.execute(()->{
            selectedTerm = mTermDAO.getTermByID(i);
        });
        dbDelay();
        return selectedTerm;
    }
    public void update(Term term) {
        DatabaseBuilder.databaseExecutor.execute(()->{
            mTermDAO.update(term);
        });
        dbDelay();
    }
    public void delete(Term term) {
        DatabaseBuilder.databaseExecutor.execute(()->{
            mTermDAO.delete(term);
        });
        dbDelay();
    }
    public void deleteAllTerms() {
        DatabaseBuilder.databaseExecutor.execute(()->{
            mTermDAO.deleteAll();
        });
        dbDelay();
    }

    /**
     * Courses
     */
    public List<Course> getAllCourses() {
        return mAllCourses;
    }
    public void insert(Course course) {
        DatabaseBuilder.databaseExecutor.execute(()->{
            mCourseDAO.insert(course);
        });
        dbDelay();
    }
    public List<Course> getCoursesByTermID(int i) {
        DatabaseBuilder.databaseExecutor.execute(()->{
            filteredCourses = mCourseDAO.getCoursesByTermID(i);
        });
        dbDelay();
        return filteredCourses;
    }
    public Course getCourseByID(int i) {
        DatabaseBuilder.databaseExecutor.execute(()->{
            selectedCourse = mCourseDAO.getCourseByID(i);
        });
        dbDelay();
        return selectedCourse;
    }
    public void update(Course course) {
        DatabaseBuilder.databaseExecutor.execute(()->{
            mCourseDAO.update(course);
        });
        dbDelay();
    }
    public void delete(Course course) {
        DatabaseBuilder.databaseExecutor.execute(()->{
            mCourseDAO.delete(course);
        });
        dbDelay();
    }
    public void deleteAllCourses() {
        DatabaseBuilder.databaseExecutor.execute(()->{
            mCourseDAO.deleteAll();
        });
        dbDelay();
    }

    /**
     * Assessments
     */
    public List<Assessment> getAllAssessments() {
        return mAllAssessments;
    }
    public void insert(Assessment assessment) {
        DatabaseBuilder.databaseExecutor.execute(()->{
            mAssessmentDAO.insert(assessment);
        });
        dbDelay();
    }
    public List<Assessment> getAssessmentsByCourseID(int i) {
        DatabaseBuilder.databaseExecutor.execute(()->{
            filteredAssessments = mAssessmentDAO.getAllAssessmentsByCourseID(i);
        });
        dbDelay();
        return filteredAssessments;
    }
    public Assessment getAssessmentByID(int i) {
        DatabaseBuilder.databaseExecutor.execute(()->{
            selectedAssessment = mAssessmentDAO.getAssessmentByID(i);
        });
        dbDelay();
    return selectedAssessment;
    }
    public void update(Assessment assessment) {
        DatabaseBuilder.databaseExecutor.execute(()->{
            mAssessmentDAO.update(assessment);
        });
        dbDelay();
    }
    public void delete(Assessment assessment) {
        DatabaseBuilder.databaseExecutor.execute(()->{
            mAssessmentDAO.delete(assessment);
        });
        dbDelay();
    }
    public void deleteAllAssessments() {
        DatabaseBuilder.databaseExecutor.execute(()->{
            mAssessmentDAO.deleteAll();
        });
        dbDelay();
    }

}
