package com.artschool.model.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Date {

    private static final String RANGE_DELIMITER = " - ";
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy");
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("kk:mm");

    @Id
    @Column(name = "date_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalTime startTime;

    private LocalTime endTime;

    @OneToOne(mappedBy = "date")
    private Course course;

    public Date() {
    }

    public Date(String dateRange, String startTime, String endTime) {
        parseDateRange(dateRange);
        parseTimeRange(startTime, endTime);
    }

    public Date(String dateRange, LocalTime startTime, LocalTime endTime) {
        parseDateRange(dateRange);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Date(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    private void parseTimeRange(String startTime, String endTime){
        this.startTime = LocalTime.parse(startTime, timeFormatter);
        this.endTime = LocalTime.parse(endTime, timeFormatter);
    }

    private void parseDateRange(String dateRange){
        String[] dates = dateRange.split(RANGE_DELIMITER);
        this.startDate = LocalDate.parse(dates[0], dateFormatter);
        this.endDate = LocalDate.parse(dates[1], dateFormatter);
    }

    public long getId() {
        return id;
    }

    public String getFormattedStartDate(){
        return dateFormatter.format(startDate);
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String getFormattedEndDate(){
        return dateFormatter.format(endDate);
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return "Date{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Date that = (Date) o;

        if (id == that.id) return false;
        if(startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;
        if(endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) return false;
        if(startTime != null ? !startTime.equals(that.startTime) : that.startTime != null) return false;
        if(endTime != null ? !endTime.equals(that.endTime) : that.endTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int hash = (int) id;
        hash = 31 * hash + (startDate != null ? startDate.hashCode() : 0);
        hash = 31 * hash + (endDate != null ? endDate.hashCode() : 0);
        hash = 31 * hash + (startTime != null ? startTime.hashCode() : 0);
        hash = 31 * hash + (endTime != null ? endTime.hashCode() : 0);
        return hash;
    }
}
