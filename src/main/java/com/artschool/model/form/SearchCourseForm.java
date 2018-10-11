package com.artschool.model.form;

import com.artschool.model.enumeration.Audience;

import java.time.DayOfWeek;

public class SearchCourseForm {

    private String request;

    private String discipline;

    private Audience audience;

    private String instructor;

    private Integer fromFee;

    private Integer toFee;

    private DayOfWeek day;

    public SearchCourseForm() {
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    public Audience getAudience() {
        return audience;
    }

    public void setAudience(Audience audience) {
        this.audience = audience;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public Integer getFromFee() {
        return fromFee;
    }

    public void setFromFee(Integer fromFee) {
        this.fromFee = fromFee;
    }

    public Integer getToFee() {
        return toFee;
    }

    public void setToFee(Integer toFee) {
        this.toFee = toFee;
    }

    public DayOfWeek getDay() {
        return day;
    }

    public void setDay(DayOfWeek day) {
        this.day = day;
    }
}