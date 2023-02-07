package wgu.c196.c196scheduler_niermeyer.models;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import java.util.List;

import wgu.c196.c196scheduler_niermeyer.components.Assessment;
import wgu.c196.c196scheduler_niermeyer.database.Repository;

public class AssessmentViewModel extends AndroidViewModel {
    private final Repository mRepository;
    private final List<Assessment> mAllAssessments;

    public AssessmentViewModel(Application application) {
        super(application);
        mRepository = new Repository(application);
        mAllAssessments = mRepository.getAllAssessments();
    }

    public void insert(Assessment assessment) { mRepository.insert(assessment); }
    public void update(Assessment assessment) { mRepository.update(assessment); }
    public List<Assessment> getAllAssessments() { return mAllAssessments; }
    public List<Assessment> getAssessmentsByCourseID(int i) {
        return mRepository.getAssessmentsByCourseID(i);
    }
    public Assessment getAssessmentByID(int i) {
        return mRepository.getAssessmentByID(i);
    }
    public void delete(Assessment assessment) { mRepository.delete(assessment); }
    public void deleteAll() { mRepository.deleteAllAssessments(); }

}
