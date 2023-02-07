package wgu.c196.c196scheduler_niermeyer.models;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import wgu.c196.c196scheduler_niermeyer.components.Term;
import wgu.c196.c196scheduler_niermeyer.database.Repository;

public class TermViewModel extends AndroidViewModel {
    private final Repository mRepository;
    private final LiveData<List<Term>> mAllTerms;

    public TermViewModel (Application application) {
        super(application);
        mRepository = new Repository(application);
        mAllTerms = mRepository.getAllTerms();
    }

    public void insert(Term term) { mRepository.insert(term); }
    public void update(Term term) { mRepository.update(term); }
    public LiveData<List<Term>> getAllTerms() { return mAllTerms; }

    public Term getTermByID(int i) { return mRepository.getTermByID(i); }
    public void delete(Term term) { mRepository.delete(term); }
    public void deleteAll() { mRepository.deleteAllTerms(); }

    /**
     *     @Query("SELECT * from terms WHERE id = :termID")
     *     LiveData<Term> getTermByID(int termID);
     */

}
