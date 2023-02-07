package wgu.c196.c196scheduler_niermeyer.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import wgu.c196.c196scheduler_niermeyer.components.Assessment;
import wgu.c196.c196scheduler_niermeyer.components.Course;
import wgu.c196.c196scheduler_niermeyer.components.Term;
import wgu.c196.c196scheduler_niermeyer.dao.AssessmentDAO;
import wgu.c196.c196scheduler_niermeyer.dao.CourseDAO;
import wgu.c196.c196scheduler_niermeyer.dao.TermDAO;

@Database(entities = {Term.class, Course.class, Assessment.class}, version=2, exportSchema = false)
public abstract class DatabaseBuilder extends RoomDatabase {
    public abstract AssessmentDAO assessmentDAO();
    public abstract CourseDAO courseDAO();
    public abstract TermDAO termDAO();

    private static volatile DatabaseBuilder INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static DatabaseBuilder getDatabase(final Context context) {
        if(INSTANCE == null){
            synchronized (DatabaseBuilder.class) {
                if (INSTANCE==null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DatabaseBuilder.class, "C196Database.db")
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // This line deletes data every app restart, comment out to make it persistent
            databaseExecutor.execute(()->{
                // populate database in the background
                TermDAO dao = INSTANCE.termDAO();
                dao.deleteAll();

                Term term = new Term("Test Term 1", "2023-01-28", "2023-06-01");
                dao.insert(term);
                term = new Term("Test Term 2", "2023-01-29", "2023-06-02");
                dao.insert(term);
            });
        }
    };
}
