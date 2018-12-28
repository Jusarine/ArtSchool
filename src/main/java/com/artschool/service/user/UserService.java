package com.artschool.service.user;

import com.artschool.model.entity.*;
import com.artschool.model.form.SignUpStudentForm;
import com.artschool.model.form.SignUpInstructorForm;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Set;

public interface UserService {

    Student createStudent(Student student);

    Student createStudent(SignUpStudentForm form, PasswordEncoder passwordEncoder);

    Instructor createInstructor(SignUpInstructorForm form, PasswordEncoder passwordEncoder);

    Instructor createInstructor(Instructor instructor);

    void editStatus(String userEmail, String status);

    void saveOrUpdate(CustomUser customUser);

    Set<Course> getStudentCourses(String studentEmail);

    Set<Course> getInstructorCourses(String instructorEmail);

    Student findStudentById(long id);

    Instructor findInstructorById(long id);

    CustomUser findById(long id);

    Student findStudentByResetToken(PasswordResetToken resetToken);

    Instructor findInstructorByResetToken(PasswordResetToken resetToken);

    CustomUser findByResetToken(PasswordResetToken resetToken);

    Set<Student> findStudentsByName(String name);

    Set<Instructor> findInstructorsByName(String name);

    Student findStudentByEmail(String email);

    Student findStudentByEmailAndInit(String email);

    Instructor findInstructorByEmail(String email);

    Instructor findInstructorByEmailAndInit(String email);

    CustomUser findUserByEmailAndInit(String email);

    CustomUser findByEmail(String email);

    Student findStudentByName(String name);

    Instructor findInstructorByName(String name);

    List<Student> findStudents();

    List<Instructor> findInstructors();
}
