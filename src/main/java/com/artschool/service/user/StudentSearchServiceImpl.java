package com.artschool.service.user;

import com.artschool.model.entity.Course;
import com.artschool.model.entity.Student;
import com.artschool.service.course.CourseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class StudentSearchServiceImpl implements StudentSearchService {

    private final UserService userService;

    private final CourseService courseService;

    private boolean retain = false;

    public StudentSearchServiceImpl(UserService userService, CourseService courseService) {
        this.userService = userService;
        this.courseService = courseService;
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Student> findStudents(String name, Integer course) {
        retain = false;
        return findAll(findByCourse(course, findByRequest(name, new HashSet<>())));
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Student> findByRequest(String name, Set<Student> result) {
        if (name != null && !"".equals(name)) {
            retainOrAdd(result, userService.findStudentsByName(name));
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Student> findByCourse(Integer course, Set<Student> result) {
        if (course != null) {
            Course c = courseService.findCourseById(course);
            if (c != null) {
                retainOrAdd(result, c.getStudents());
            }
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Student> findAll(Set<Student> result) {
        if (!retain) result.addAll(userService.findStudents());
        return result;
    }

    private void retainOrAdd(Set<Student> to, Set<Student> from) {
        if (retain) to.retainAll(from);
        else {
            to.addAll(from);
            retain = true;
        }
    }
}