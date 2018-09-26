package com.artschool.repository;

import com.artschool.model.entity.PasswordResetToken;
import com.artschool.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long>{

    Student findStudentByEmail(String email);

    Student findStudentByResetToken(PasswordResetToken resetToken);
}
