package wgu.c196.c196scheduler_niermeyer.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import wgu.c196.c196scheduler_niermeyer.components.Term;

@Dao
public interface TermDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Term term);
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Term term);
    @Query("SELECT * FROM terms ORDER BY id ASC")
    LiveData<List<Term>> getAllTerms();
    @Query("SELECT * from terms WHERE id = :id")
    Term getTermByID(int id);
    @Delete
    void delete(Term term);
    @Query("DELETE FROM terms")
    void deleteAll();

}
