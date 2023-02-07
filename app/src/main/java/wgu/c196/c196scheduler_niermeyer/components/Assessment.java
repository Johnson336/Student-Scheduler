package wgu.c196.c196scheduler_niermeyer.components;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="assessments")
public class Assessment {
    @PrimaryKey(autoGenerate=true)
    private int id;
    private String title;
    private String startDate;
    private String endDate;
    private int courseID;

    public Assessment() {

    }
    public Assessment(String title, String startDate, String endDate) {
        this.id = 0;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    @NonNull
    @Override
    public String toString() {
        return this.title;
    }
    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
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
}
