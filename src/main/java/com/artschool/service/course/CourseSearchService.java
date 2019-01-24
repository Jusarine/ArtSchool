package com.artschool.service.course;

import com.artschool.model.entity.Course;
import com.artschool.model.enumeration.Audience;
import com.artschool.model.form.SearchCourseForm;

import java.time.DayOfWeek;
import java.util.Set;

public interface CourseSearchService {

    Set<Course> findCourses(SearchCourseForm form);

    Set<Course> findByRequest(String name, Set<Course> result);

    Set<Course> findByDiscipline(String discipline, Set<Course> result);

    Set<Course> findByAudience(Audience audience, Set<Course> result);

    Set<Course> findByInstructor(String instructor, Set<Course> result);

    Set<Course> findByFee(Integer fromFee, Integer toFee, Set<Course> result);

    Set<Course> findByDay(DayOfWeek day, Set<Course> result);

    Set<Course> findAll(Set<Course> result);
}
