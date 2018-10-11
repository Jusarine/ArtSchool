package com.artschool.service.course;

import com.artschool.model.entity.Course;
import com.artschool.model.enumeration.Audience;
import com.artschool.model.form.SearchCourseForm;

import java.util.Set;

public interface SearchService {

    Set<Course> findCourses(SearchCourseForm form);

    Set<Course> findByRequest(String request, Set<Course> result);

    Set<Course> findByDiscipline(String discipline, Set<Course> result);

    Set<Course> findByAudience(Audience audience, Set<Course> result);

    Set<Course> findByInstructor(String instructor, Set<Course> result);

    Set<Course> findAll(Set<Course> result);
}
