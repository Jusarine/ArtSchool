package com.artschool.service.course;

import com.artschool.model.entity.*;
import com.artschool.model.enumeration.Audience;
import com.artschool.model.form.CourseForm;
import com.artschool.repository.CourseRepository;
import com.artschool.repository.InstructorRepository;
import com.artschool.repository.StudentRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    private final StudentRepository studentRepository;

    private final InstructorRepository instructorRepository;

    private final DayService dayService;

    private final DateService dateService;

    private final DisciplineService disciplineService;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, StudentRepository studentRepository,
                             InstructorRepository instructorRepository, DayService dayService, DateService dateService,
                             DisciplineService disciplineService) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.instructorRepository = instructorRepository;
        this.dayService = dayService;
        this.dateService = dateService;
        this.disciplineService = disciplineService;
    }

    @Override
    @Transactional
    public Course createCourse(Course course) {
        if (courseRepository.findCourseByName(course.getName()) != null) return null;
        course.getInstructor().addCourse(course);
        courseRepository.save(course);
        return course;
    }

    @Override
    @Transactional
    public Course createCourse(CourseForm form, String instructorEmail) {
        if (courseRepository.findCourseByName(form.getName()) != null) return null;
        Course course = new Course(form.getName(), disciplineService.getDisciplines(form.getDisciplines()),
                form.getAudience(), form.getAvailableSpaces(), form.getFee(), dateService.createDate(form),
                dayService.getDays(form.getDays()), form.getDescription(),
                instructorRepository.findInstructorByEmail(instructorEmail));
        course.getInstructor().addCourse(course);
        courseRepository.save(course);
        return course;
    }

    @Override
    @Transactional
    public Course updateCourse(Course course) {
        Course found = courseRepository.findCourseByName(course.getName());
        if (found != null && !(found.getId() == course.getId())) return null;
        courseRepository.save(course);
        return course;
    }

    @Override
    @Transactional
    public Course updateCourse(long courseId, CourseForm form) {
        Course found = courseRepository.findCourseByName(form.getName());
        if (found != null && !(found.getId() == courseId)) return null;
        Course course = new Course(courseId, form.getName(), disciplineService.getDisciplines(form.getDisciplines()),
                form.getAudience(), form.getAvailableSpaces(), form.getFee(), dateService.createDate(form),
                dayService.getDays(form.getDays()), form.getDescription(), findCourseById(courseId).getInstructor());
        courseRepository.save(course);
        return course;
    }

    @Override
    @Transactional
    public void saveOrUpdate(Course course) {
        courseRepository.save(course);
    }

    @Override
    @Transactional
    public void enrollInCourse(String studentEmail, long courseId) {
        Course course = findCourseById(courseId);
        Student student = studentRepository.findStudentByEmail(studentEmail);
        course.decrementAvailableSpaces();
        course.addStudent(student);
        student.addCourse(course);
        courseRepository.save(course);
    }

    @Override
    @Transactional
    public void unenrollFromCourse(String studentEmail, long courseId) {
        Course course = findCourseById(courseId);
        Student student = studentRepository.findStudentByEmail(studentEmail);
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
    public Course findCourseByName(String name) {
        return courseRepository.findCourseByName(name);
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
