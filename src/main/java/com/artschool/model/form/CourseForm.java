package com.artschool.model.form;

import com.artschool.model.enumeration.Audience;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class CourseForm {

    private String name;

    private String[] disciplines;

    private Audience audience;

    private Integer spaces;

    private Integer fee;

    private String date;

    private LocalTime startTime;

    private LocalTime endTime;

    private DayOfWeek[] days;

    private String description;

    public CourseForm() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getDisciplines() {
        return disciplines;
    }

    public void setDisciplines(String[] disciplines) {
        this.disciplines = disciplines;
    }

    public Audience getAudience() {
        return audience;
    }

    public void setAudience(Audience audience) {
        this.audience = audience;
    }

    public Integer getSpaces() {
        return spaces;
    }

    public void setSpaces(Integer spaces) {
        this.spaces = spaces;
    }

    public Integer getFee() {
        return fee;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public DayOfWeek[] getDays() {
        return days;
    }

    public void setDays(DayOfWeek[] days) {
        this.days = days;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
