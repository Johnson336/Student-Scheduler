package wgu.c196.c196scheduler_niermeyer.components;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="terms")
public class Term {
    @PrimaryKey(autoGenerate=true)
    private int id;
    private String name;
    private String startDate;
    private String endDate;

    public Term() {

    }
    public Term(String name, String startDate, String endDate) {
        this.id = 0;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    // Setters
    public void setId(int i) {
        this.id = i;
    }
    public void setName(String s) {
        this.name = s;
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
    public String getName() {
        return this.name;
    }
    public String getStartDate() {
        return this.startDate;
    }
    public String getEndDate() {
        return this.endDate;
    }
}
