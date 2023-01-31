package wgu.c196.c196scheduler_niermeyer.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import wgu.c196.c196scheduler_niermeyer.components.Assessment;
import wgu.c196.c196scheduler_niermeyer.components.Course;
import wgu.c196.c196scheduler_niermeyer.components.Mentor;
import wgu.c196.c196scheduler_niermeyer.components.Term;
import wgu.c196.c196scheduler_niermeyer.dao.AssessmentDAO;
import wgu.c196.c196scheduler_niermeyer.dao.CourseDAO;
import wgu.c196.c196scheduler_niermeyer.dao.MentorDAO;
import wgu.c196.c196scheduler_niermeyer.dao.TermDAO;

@Database(entities = {Term.class, Course.class, Assessment.class, Mentor.class}, version=1, exportSchema = false)
public abstract class DatabaseBuilder extends RoomDatabase {
    public abstract MentorDAO mentorDAO();
    public abstract AssessmentDAO assessmentDAO();
    public abstract CourseDAO courseDAO();
    public abstract TermDAO termDAO();

    private static volatile DatabaseBuilder INSTANCE;

    static DatabaseBuilder getDatabase(final Context context){
        if(INSTANCE==null){
            synchronized (DatabaseBuilder.class){
                if (INSTANCE==null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DatabaseBuilder.class, "C196Database.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
