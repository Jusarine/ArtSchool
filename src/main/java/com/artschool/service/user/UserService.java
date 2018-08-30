package com.artschool.service.user;

import com.artschool.model.entity.CustomUser;
import com.artschool.model.enumeration.Gender;
import com.artschool.model.entity.Instructor;
import com.artschool.model.entity.Student;

import java.util.List;
import java.util.Set;

public interface UserService {

    Student createStudent(String firstName, String lastName, Gender gender, String phoneNumber, String email, String password);

    Student createStudent(Student student);

    Instructor createInstructor(String firstName, String lastName, Gender gender, String phoneNumber, String email, String password);

    Instructor createInstructor(Instructor instructor);

    Student reinitializeStudent(Student student);

    Instructor reinitializeInstructor(Instructor instructor);

    Student findStudentById(long id);

    Instructor findInstructorById(long id);

    CustomUser findById(long id);

    Set<Instructor> findByName(String name);

    Student findStudentByEmail(String email);

    Instructor findInstructorByEmail(String email);

    CustomUser findByEmail(String email);

    List<Student> findStudents();

    List<Instructor> findInstructors();
}
