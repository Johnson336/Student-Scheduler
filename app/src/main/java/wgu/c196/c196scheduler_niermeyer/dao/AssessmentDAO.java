package wgu.c196.c196scheduler_niermeyer.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

import wgu.c196.c196scheduler_niermeyer.components.Assessment;

@Dao
public interface AssessmentDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Assessment assessment);
    @Update
    void update(Assessment assessment);
    @Delete
    void delete(Assessment assessment);
    @Query("SELECT * FROM assessments ORDER BY id ASC")
    List<Assessment> getAllAssessments();
    @Query("SELECT * FROM assessments WHERE courseID = :courseID ORDER BY id ASC")
    List<Assessment> getAllAssociatedAssessments(int courseID);
}
