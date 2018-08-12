package com.artschool;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InstructorRepository extends JpaRepository<Instructor, Long>{

    @Query("SELECT i FROM Instructor i where i.email = :email")
    Instructor findByEmail(@Param("email") String email);
}
