package com.artschool.repository;

import com.artschool.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long>{

    Student findStudentById(long id);

    Student findStudentByEmail(String email);
}
