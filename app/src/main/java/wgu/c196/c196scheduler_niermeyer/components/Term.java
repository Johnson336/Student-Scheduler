package wgu.c196.c196scheduler_niermeyer.components;

import java.time.LocalDate;
import java.util.ArrayList;

public class Term {
    private int id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private ArrayList<Course> courses;

    public Term() {

    }
    public Term(String name, LocalDate startDate, LocalDate endDate) {
        this.id = 0;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.courses = new ArrayList<Course>();
    }
    // Setters
    public void setId(int i) {
        this.id = i;
    }
    public void setName(String s) {
        this.name = s;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    public boolean addCourse(Course c) {
        return this.courses.add(c);
    }
    public boolean removeCourse(Course c) {
        return this.courses.remove(c);
    }
    public void clearCourses() {
        this.courses.clear();
    }
    // Getters
    public int getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public LocalDate getStartDate() {
        return this.startDate;
    }
    public LocalDate getEndDate() {
        return this.endDate;
    }
    public ArrayList<Course> getCourses() {
        return this.courses;
    }
}
