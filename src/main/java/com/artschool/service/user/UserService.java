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

    Student reinitializeStudent(Student student);

    Instructor reinitializeInstructor(Instructor instructor);

    void editStatus(CustomUser customUser, String composition);

    void saveOrUpdate(CustomUser customUser);

    Student findStudentById(long id);

    Instructor findInstructorById(long id);

    CustomUser findById(long id);

    Student findStudentByResetToken(PasswordResetToken resetToken);

    Instructor findInstructorByResetToken(PasswordResetToken resetToken);

    CustomUser findByResetToken(PasswordResetToken resetToken);

    Set<Student> findStudentsByName(String name);

    Set<Instructor> findInstructorsByName(String name);

    Student findStudentByEmail(String email);

    Instructor findInstructorByEmail(String email);

    CustomUser findByEmail(String email);

    Student findStudentByName(String name);

    Instructor findInstructorByName(String name);

    List<Student> findStudents();

    List<Instructor> findInstructors();
}
