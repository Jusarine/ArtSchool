package com.artschool.service.course;

import com.artschool.model.entity.Date;
import com.artschool.model.form.CourseForm;

public interface DateService {

    Date createDate(Date date);

    Date createDate(CourseForm form);
}
