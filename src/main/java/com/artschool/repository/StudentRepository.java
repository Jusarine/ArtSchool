package com.artschool.repository;

import com.artschool.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long>{

    Student findByEmail(String email);
}
