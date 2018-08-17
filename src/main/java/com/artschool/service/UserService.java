package com.artschool.service;

import com.artschool.model.CustomUser;
import com.artschool.model.Instructor;
import com.artschool.model.Student;

import java.util.List;

public interface UserService {

    Student createStudent(String firstName, String lastName, String phoneNumber, String email, String password);

    Student createStudent(Student student);

    Instructor createInstructor(String firstName, String lastName, String phoneNumber, String email, String password);

    Instructor createInstructor(Instructor instructor);

    Student findStudentByEmail(String email);

    Instructor findInstructorByEmail(String email);

    CustomUser findByEmail(String email);

    List<Student> findStudents();

    List<Instructor> findInstructors();
}
