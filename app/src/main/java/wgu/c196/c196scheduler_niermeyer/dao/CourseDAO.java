package wgu.c196.c196scheduler_niermeyer.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

import wgu.c196.c196scheduler_niermeyer.components.Course;

@Dao
public interface CourseDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Course course);
    @Update
    void update(Course course);
    @Delete
    void delete(Course course);
    @Query("SELECT * FROM courses ORDER BY id ASC")
    List<Course> getAllCourses();
    @Query("SELECT * FROM courses WHERE termID = :termID ORDER BY id ASC")
    List<Course> getAllAssociatedCourses(int termID);
}
