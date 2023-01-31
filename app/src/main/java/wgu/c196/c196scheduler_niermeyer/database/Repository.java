package wgu.c196.c196scheduler_niermeyer.database;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import wgu.c196.c196scheduler_niermeyer.components.Assessment;
import wgu.c196.c196scheduler_niermeyer.components.Course;
import wgu.c196.c196scheduler_niermeyer.components.Mentor;
import wgu.c196.c196scheduler_niermeyer.components.Term;
import wgu.c196.c196scheduler_niermeyer.dao.AssessmentDAO;
import wgu.c196.c196scheduler_niermeyer.dao.CourseDAO;
import wgu.c196.c196scheduler_niermeyer.dao.MentorDAO;
import wgu.c196.c196scheduler_niermeyer.dao.TermDAO;

public class Repository {
    private MentorDAO mMentorDAO;
    private AssessmentDAO mAssessmentDAO;
    private CourseDAO mCourseDAO;
    private TermDAO mTermDAO;
    private List<Mentor> mAllMentors;
    private List<Assessment> mAllAssessments;
    private List<Course> mAllCourses;
    private List<Term> mAllTerms;

    private static int NUMBER_OF_THREADS=4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application) {
        DatabaseBuilder db = DatabaseBuilder.getDatabase(application);
        mMentorDAO = db.mentorDAO();
        mAssessmentDAO = db.assessmentDAO();
        mCourseDAO = db.courseDAO();
        mTermDAO = db.termDAO();
    }

    /**
     * Terms
     */
    public List<Term> getAllTerms() {
        databaseExecutor.execute(()->{
            mAllTerms = mTermDAO.getAllTerms();
        });
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllTerms;
    }
    public void insert(Term term) {
        databaseExecutor.execute(()->{
            mTermDAO.insert(term);
        });
        try {
            Thread.sleep(100);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void update(Term term) {
        databaseExecutor.execute(()->{
            mTermDAO.update(term);
        });
        try {
            Thread.sleep(100);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void delete(Term term) {
        databaseExecutor.execute(()->{
            mTermDAO.delete(term);
        });
        try {
            Thread.sleep(100);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Courses
     */
    public List<Course> getAllCourses() {
        databaseExecutor.execute(()->{
            mAllCourses = mCourseDAO.getAllCourses();
        });
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllCourses;
    }
    public void insert(Course course) {
        databaseExecutor.execute(()->{
            mCourseDAO.insert(course);
        });
        try {
            Thread.sleep(100);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void update(Course course) {
        databaseExecutor.execute(()->{
            mCourseDAO.update(course);
        });
        try {
            Thread.sleep(100);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void delete(Course course) {
        databaseExecutor.execute(()->{
            mCourseDAO.delete(course);
        });
        try {
            Thread.sleep(100);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Assessments
     */
    public List<Assessment> getAllAssessments() {
        databaseExecutor.execute(()->{
            mAllAssessments = mAssessmentDAO.getAllAssessments();
        });
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllAssessments;
    }
    public void insert(Assessment assessment) {
        databaseExecutor.execute(()->{
            mAssessmentDAO.insert(assessment);
        });
        try {
            Thread.sleep(100);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void update(Assessment assessment) {
        databaseExecutor.execute(()->{
            mAssessmentDAO.update(assessment);
        });
        try {
            Thread.sleep(100);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void delete(Assessment assessment) {
        databaseExecutor.execute(()->{
            mAssessmentDAO.delete(assessment);
        });
        try {
            Thread.sleep(100);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Mentors
     */
    public List<Mentor> getAllMentors() {
        databaseExecutor.execute(()->{
            mAllMentors = mMentorDAO.getAllMentors();
        });
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllMentors;
    }
    public void insert(Mentor mentor) {
        databaseExecutor.execute(()->{
            mMentorDAO.insert(mentor);
        });
        try {
            Thread.sleep(100);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void update(Mentor mentor) {
        databaseExecutor.execute(()->{
            mMentorDAO.update(mentor);
        });
        try {
            Thread.sleep(100);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void delete(Mentor mentor) {
        databaseExecutor.execute(()->{
            mMentorDAO.delete(mentor);
        });
        try {
            Thread.sleep(100);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
}
