package com.artschool.service.course;

import com.artschool.model.entity.*;
import com.artschool.model.enumeration.Audience;
import com.artschool.model.form.CourseForm;
import com.artschool.repository.CourseRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class CourseServiceImpl implements CourseService{

    private final CourseRepository courseRepository;

    private final DayService dayService;

    private final DateService dateService;

    private final DisciplineService disciplineService;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, DayService dayService, DateService dateService, DisciplineService disciplineService) {
        this.courseRepository = courseRepository;
        this.dayService = dayService;
        this.dateService = dateService;
        this.disciplineService = disciplineService;
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
    public void createCourse(CourseForm form, Instructor instructor) {
        Course course = new Course(form.getName(), disciplineService.getDisciplines(form.getDisciplines()),
                form.getAudience(), form.getAvailableSpaces(), form.getFee(), dateService.createDate(form),
                dayService.getDays(form.getDays()), form.getDescription(), instructor);
        course.getInstructor().addCourse(course);
        courseRepository.save(course);
    }

    @Override
    @Transactional
    public Course updateCourse(Course course) {
        courseRepository.save(course);
        return course;
    }

    @Override
    @Transactional
    public void updateCourse(long courseId, CourseForm form) {
        Course course = new Course(courseId, form.getName(), disciplineService.getDisciplines(form.getDisciplines()),
                form.getAudience(), form.getAvailableSpaces(), form.getFee(), dateService.createDate(form), dayService.getDays(form.getDays()),
                form.getDescription(), findCourseById(courseId).getInstructor());
        courseRepository.save(course);
    }

    @Override
    @Transactional
    public void saveOrUpdate(Course course) {
        courseRepository.save(course);
    }

    @Override
    @Transactional
    public void enrollInCourse(Student student, Course course) {
        course.decrementAvailableSpaces();
        course.addStudent(student);
        student.addCourse(course);
        courseRepository.save(course);
    }

    @Override
    @Transactional
    public void unenrollFromCourse(Student student, Course course) {
        course.incrementAvailableSpaces();
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
    public void deleteCourse(long courseId) {
        courseRepository.deleteById(courseId);
    }

    @Override
    @Transactional(readOnly = true)
    public Course findCourseById(long id) {
        return courseRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Course findCourseByIdAndInit(long id) {
        Course course = findCourseById(id);
        if (course != null) {
            Hibernate.initialize(course.getDisciplines());
            Hibernate.initialize(course.getDays());
            Hibernate.initialize(course.getStudents());
        }
        return course;
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Course> findCoursesByName(String name) {
        return courseRepository.findCoursesByNameContaining(name);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Course> findCoursesByDiscipline(Discipline discipline) {
        return courseRepository.findCoursesByDisciplines(discipline);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Course> findCoursesByAudience(Audience audience) {
        return courseRepository.findCoursesByAudience(audience);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Course> findCoursesByInstructor(Instructor instructor) {
        return courseRepository.findCoursesByInstructor(instructor);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Course> findCoursesByFeeBefore(Integer toFee) {
        return courseRepository.findCoursesByFeeLessThanEqual(toFee);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Course> findCoursesByFeeAfter(Integer fromFee) {
        return courseRepository.findCoursesByFeeGreaterThanEqual(fromFee);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Course> findCoursesByFeeBetween(Integer fromFee, Integer toFee) {
        return courseRepository.findCoursesByFeeBetween(fromFee, toFee);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer findMinCourseFee() {
        return courseRepository.findMinCourseFee();
    }

    @Override
    @Transactional(readOnly = true)
    public Integer findMaxCourseFee() {
        return courseRepository.findMaxCourseFee();
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Course> findCoursesByDay(Day day) {
        return courseRepository.findCoursesByDays(day);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Course> findCourses() {
        return courseRepository.findAll();
    }
}
