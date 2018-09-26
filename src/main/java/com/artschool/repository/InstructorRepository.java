package com.artschool.repository;

import com.artschool.model.entity.Instructor;
import com.artschool.model.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface InstructorRepository extends JpaRepository<Instructor, Long>{

    Instructor findInstructorByEmail(String email);

    Instructor findInstructorByResetToken(PasswordResetToken resetToken);

    Set<Instructor> findInstructorsByFirstNameContaining(String firstName);

    Set<Instructor> findInstructorsByLastNameContaining(String lastName);

    Set<Instructor> findInstructorsByFirstNameContainingAndLastNameContaining(String firstName, String lastName);
}
