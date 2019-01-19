package com.artschool.repository;

import com.artschool.model.entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {

    Instructor findInstructorByEmail(String email);

    Instructor findInstructorByFirstNameAndLastName(String firstName, String lastName);

    Set<Instructor> findInstructorsByFirstNameContaining(String firstName);

    Set<Instructor> findInstructorsByLastNameContaining(String lastName);

    Set<Instructor> findInstructorsByFirstNameContainingAndLastNameContaining(String firstName, String lastName);
}
