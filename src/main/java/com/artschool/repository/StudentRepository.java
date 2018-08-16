package com.artschool.repository;

import com.artschool.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentRepository extends JpaRepository<Student, Long>{

    @Query("SELECT s FROM Student s where s.email = :email")
    Student findByEmail(@Param("email") String email);
}
