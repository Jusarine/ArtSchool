package com.artschool.service.course;

import com.artschool.model.entity.Day;

import java.time.DayOfWeek;
import java.util.List;

public interface DayService {

    void addDay(DayOfWeek day);

    void addDays(DayOfWeek... days);

    Day getDay(DayOfWeek day);

    List<Day> getDays(DayOfWeek... days);

    List<Day> findDays();
}
