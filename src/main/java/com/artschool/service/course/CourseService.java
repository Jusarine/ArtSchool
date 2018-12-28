package com.artschool.service.course;

import com.artschool.model.entity.*;
import com.artschool.model.enumeration.Audience;
import com.artschool.model.form.CourseForm;

import java.util.List;
import java.util.Set;

public interface CourseService {

    Course createCourse(Course course);

    void createCourse(CourseForm form, String instructorEmail);

    Course updateCourse(Course course);

    void updateCourse(long courseId, CourseForm form);

    void saveOrUpdate(Course course);

    void enrollInCourse(String studentEmail, long courseId);

    void unenrollFromCourse(String studentEmail, long courseId);

    boolean isEnrolled(Student student, Course course);

    boolean isAuthor(Instructor instructor, Course course);

    void deleteCourse(long courseId);

    Course findCourseById(long id);

    Course findCourseByIdAndInit(long id);

    Set<Course> findCoursesByName(String name);

    Set<Course> findCoursesByDiscipline(Discipline discipline);

    Set<Course> findCoursesByAudience(Audience audience);

    Set<Course> findCoursesByInstructor(Instructor instructor);

    Set<Course> findCoursesByFeeBefore(Integer toFee);

    Set<Course> findCoursesByFeeAfter(Integer fromFee);

    Set<Course> findCoursesByFeeBetween(Integer fromFee, Integer toFee);

    Integer findMinCourseFee();

    Integer findMaxCourseFee();

    Set<Course> findCoursesByDay(Day day);

    List<Course> findCourses();
}
