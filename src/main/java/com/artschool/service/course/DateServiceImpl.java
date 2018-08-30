package com.artschool.service.course;

import com.artschool.model.entity.Date;
import com.artschool.repository.DateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class DateServiceImpl implements DateService{

    private final DateRepository dateRepository;

    @Autowired
    public DateServiceImpl(DateRepository dateRepository) {
        this.dateRepository = dateRepository;
    }

    @Override
    @Transactional
    public Date createDate(String dateRange, String startTime, String endTime){
        Date date = new Date(dateRange, startTime, endTime);
        dateRepository.save(date);
        return date;
    }

    @Override
    @Transactional
    public Date createDate(String dateRange, LocalTime startTime, LocalTime endTime){
        Date date = new Date(dateRange, startTime, endTime);
        dateRepository.save(date);
        return date;
    }

    @Override
    @Transactional
    public Date createDate(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime){
        Date date = new Date(startDate, endDate, startTime, endTime);
        dateRepository.save(date);
        return date;
    }

    @Override
    @Transactional
    public Date createDate(Date date){
        dateRepository.save(date);
        return date;
    }


}
