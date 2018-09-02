package com.artschool.service.course;

import com.artschool.model.entity.Date;
import com.artschool.model.form.CourseForm;
import com.artschool.repository.DateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DateServiceImpl implements DateService{

    private final DateRepository dateRepository;

    @Autowired
    public DateServiceImpl(DateRepository dateRepository) {
        this.dateRepository = dateRepository;
    }

    @Override
    @Transactional
    public Date createDate(Date date){
        dateRepository.save(date);
        return date;
    }

    @Override
    @Transactional
    public Date createDate(CourseForm form){
        Date date = new Date(form.getDate(), form.getStartTime(), form.getEndTime());
        dateRepository.save(date);
        return date;
    }
}
