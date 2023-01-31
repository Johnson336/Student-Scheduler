package wgu.c196.c196scheduler_niermeyer.components;

import java.time.LocalDate;

public class Assessment {
    private int id;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    public Assessment() {

    }
    public Assessment(String title, LocalDate startDate, LocalDate endDate) {
        this.id = 0;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    // Setters
    public void setId(int id) {
        this.id = id;
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
}
