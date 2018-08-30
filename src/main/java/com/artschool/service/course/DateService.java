package com.artschool.service.course;

import com.artschool.model.entity.Date;

import java.time.LocalDate;
import java.time.LocalTime;

public interface DateService {

    Date createDate(String dateRange, String startTime, String endTime);

    Date createDate(String dateRange, LocalTime startTime, LocalTime endTime);

    Date createDate(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime);

    Date createDate(Date date);
}
