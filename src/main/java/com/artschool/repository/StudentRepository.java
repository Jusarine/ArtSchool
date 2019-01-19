package com.artschool.repository;

import com.artschool.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Student findStudentByEmail(String email);

    Student findStudentByFirstNameAndLastName(String firstName, String lastName);

    Set<Student> findStudentsByFirstNameContaining(String firstName);

    Set<Student> findStudentsByLastNameContaining(String lastName);

    Set<Student> findStudentsByFirstNameContainingAndLastNameContaining(String firstName, String lastName);
}
