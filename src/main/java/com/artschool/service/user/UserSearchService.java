package com.artschool.service.user;

import com.artschool.model.entity.Student;

import java.util.Set;

public interface UserSearchService {

    Set<Student> findStudents(String request, Integer course);

    Set<Student> findByRequest(String request, Set<Student> result);

    Set<Student> findByCourse(Integer course, Set<Student> result);

    Set<Student> findAll(Set<Student> result);
}
