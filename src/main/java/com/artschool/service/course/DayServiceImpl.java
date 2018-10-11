package com.artschool.service.course;

import com.artschool.model.entity.Day;
import com.artschool.repository.DayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

@Service
public class DayServiceImpl implements DayService{

    private final DayRepository dayRepository;

    @Autowired
    public DayServiceImpl(DayRepository dayRepository) {
        this.dayRepository = dayRepository;
    }

    @Override
    @Transactional
    public void addDay(DayOfWeek day){
        dayRepository.save(new Day(day));
    }

    @Override
    @Transactional
    public void addDays(DayOfWeek... days){
        for (DayOfWeek d : days) {
            addDay(d);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Day getDay(DayOfWeek day){
        return dayRepository.findDayByName(day);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Day> getDays(DayOfWeek... days){
        List<Day> list = new ArrayList<>();
        for (DayOfWeek day : days) {
            list.add(dayRepository.findDayByName(day));
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Day> findDays(){
        return dayRepository.findAll();
    }
}
