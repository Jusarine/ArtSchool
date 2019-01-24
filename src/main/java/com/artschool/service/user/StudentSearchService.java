package com.artschool.service.user;

import com.artschool.model.entity.Student;

import java.util.Set;

public interface StudentSearchService {

    Set<Student> findStudents(String name, Integer course);

    Set<Student> findByRequest(String name, Set<Student> result);

    Set<Student> findByCourse(Integer course, Set<Student> result);

    Set<Student> findAll(Set<Student> result);
}
