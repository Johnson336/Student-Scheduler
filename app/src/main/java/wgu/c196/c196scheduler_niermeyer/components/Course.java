package wgu.c196.c196scheduler_niermeyer.components;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="courses")
public class Course {
    @PrimaryKey(autoGenerate=true)
    private int id;
    private String title;
    private String startDate;
    private String endDate;
    private String status;
    private String note;
    private int termID;

    public Course() {

    }
    public Course(String title, String startDate, String endDate, String status, String note) {
        this.id = 0;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.note = note;
    }
    public int getTermID() {
        return termID;
    }
    public void setTermID(int termID) {
        this.termID = termID;
    }
    // Setters
    public void setId(int i) {
        this.id = i;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    public void setStatus(String status) {
        this.status = status;
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
    public String getStartDate() {
        return this.startDate;
    }
    public String getEndDate() {
        return this.endDate;
    }
    public String getStatus() {
        return this.status;
    }
    public String getNote() {
        return this.note;
    }
}
