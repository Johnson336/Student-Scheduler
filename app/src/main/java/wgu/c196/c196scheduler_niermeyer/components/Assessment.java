package wgu.c196.c196scheduler_niermeyer.components;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Locale;

@Entity(tableName="assessments")
public class Assessment {
    @PrimaryKey(autoGenerate=true)
    private int id;
    private String title;
    private int type;
    private String startDate;
    private String endDate;
    private int courseID;

    public Assessment() {

    }
    public Assessment(String title, int type, String startDate, String endDate) {
        this.id = 0;
        this.type = type;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    @NonNull
    @Override
    public String toString() {
        return String.format(Locale.US, "%d: %s (%d)", this.id, this.title, this.courseID);
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
    public void setType(int type) { this.type = type; }
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
    public int getType() { return this.type; }
    public String getStartDate() {
        return this.startDate;
    }
    public String getEndDate() {
        return this.endDate;
    }
}
