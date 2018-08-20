package com.artschool.repository;

import com.artschool.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructorRepository extends JpaRepository<Instructor, Long>{

    Instructor findInstructorById(long id);

    Instructor findInstructorByEmail(String email);
}
