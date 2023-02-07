package wgu.c196.c196scheduler_niermeyer.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import wgu.c196.c196scheduler_niermeyer.components.Course;

@Dao
public interface CourseDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Course course);
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Course course);
    @Query("SELECT * FROM courses ORDER BY id ASC")
    List<Course> getAllCourses();
    @Query("SELECT * FROM courses WHERE termID = :termID ORDER BY id ASC")
    List<Course> getCoursesByTermID(int termID);
    @Query("SELECT * FROM courses WHERE id = :courseID")
    Course getCourseByID(int courseID);
    @Delete
    void delete(Course course);
    @Query("DELETE FROM terms")
    void deleteAll();


}
