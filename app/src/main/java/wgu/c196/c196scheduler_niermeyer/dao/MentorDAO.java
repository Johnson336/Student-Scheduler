package wgu.c196.c196scheduler_niermeyer.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

import wgu.c196.c196scheduler_niermeyer.components.Mentor;

@Dao
public interface MentorDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Mentor mentor);
    @Update
    void update(Mentor mentor);
    @Delete
    void delete(Mentor mentor);
    @Query("SELECT * FROM mentors ORDER BY id ASC")
    List<Mentor> getAllMentors();
    @Query("SELECT * FROM mentors WHERE courseID = :courseID ORDER BY id ASC")
    List<Mentor> getAllAssociatedMentors(int courseID);
}
