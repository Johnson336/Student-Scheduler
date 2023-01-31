package wgu.c196.c196scheduler_niermeyer.components;

import java.time.LocalDate;
import java.util.ArrayList;

public class Course {
    private int id;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private Mentor mentor;
    private String note;
    private ArrayList<Assessment> assessments;

    public Course() {

    }
    public Course(String title, LocalDate startDate, LocalDate endDate, String status, Mentor mentor, String note) {
        this.id = 0;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.mentor = mentor;
        this.note = note;
    }
    // Setters
    public void setId(int i) {
        this.id = i;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public void setMentor(Mentor mentor) {
        this.mentor = mentor;
    }
    public void setNote(String note) {
        this.note = note;
    }
    // Getters
    public int getId() {
        return this.id;
    }
    public String getTitle() {
        return this.title;
    }
    public LocalDate getStartDate() {
        return this.startDate;
    }
    public LocalDate getEndDate() {
        return this.endDate;
    }
    public String getStatus() {
        return this.status;
    }
    public Mentor getMentor() {
        return this.mentor;
    }
    public String getNote() {
        return this.note;
    }
    public ArrayList<Assessment> getAssessments() {
        return this.assessments;
    }
    public boolean addAssessment(Assessment a) {
        return assessments.add(a);
    }
    public boolean removeAssessment(Assessment a) {
        return assessments.remove(a);
    }
    public void clearAssessments() {
        this.assessments.clear();
    }
}
