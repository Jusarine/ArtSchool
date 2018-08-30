package com.artschool.service.course;

import com.artschool.model.entity.*;
import com.artschool.model.enumeration.Audience;
import com.artschool.model.enumeration.Discipline;
import com.artschool.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class CourseServiceImpl implements CourseService{

    private final CourseRepository courseRepository;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    @Transactional
    public Course createCourse(Course course) {
        course.getInstructor().addCourse(course);
        courseRepository.save(course);
        return course;
    }

    @Override
    @Transactional
    public Course createCourse(String name, Discipline discipline, Audience audience, Integer fee, Date date, List<Day> days, String description, Instructor instructor) {
        Course course = new Course(name, discipline, audience, fee, date, days, description, instructor);
        instructor.addCourse(course);
        courseRepository.save(course);
        return course;
    }

    @Override
    @Transactional
    public void save(Course course) {
        courseRepository.save(course);
    }

    @Override
    @Transactional
    public void enrollInCourse(Student student, Course course) {
        course.addStudent(student);
        student.addCourse(course);
        courseRepository.save(course);
    }

    @Override
    @Transactional
    public void unenrollFromCourse(Student student, Course course) {
        course.removeStudent(student);
        student.removeCourse(course);
        courseRepository.save(course);
    }

    @Override
    @Transactional
    public boolean isEnrolled(Student student, Course course) {
        return course.getStudents().contains(student);
    }

    @Override
    @Transactional
    public boolean isAuthor(Instructor instructor, Course course) {
        return course.getInstructor().equals(instructor);
    }

    @Override
    @Transactional
    public void updateCourse(long id, String name, Discipline discipline, Audience audience, Integer fee, Date date, List<Day> days, String description) {
        Course course = findCourseById(id);
        course.setName(name);
        course.setDiscipline(discipline);
        course.setAudience(audience);
        course.setFee(fee);
        course.setDate(date);
        course.setDays(days);
        course.setDescription(description);
        courseRepository.save(course);
    }

    @Override
    @Transactional
    public void deleteCourse(Course course) {
        courseRepository.delete(course);
    }

    @Override
    @Transactional(readOnly = true)
    public Course findCourseById(long id) {
        return courseRepository.findCourseById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Course> findCoursesByName(String name) {
        return courseRepository.findCoursesByNameContaining(name);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Course> findCourses() {
        return courseRepository.findAll();
    }
}
