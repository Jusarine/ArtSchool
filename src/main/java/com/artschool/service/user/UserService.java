package com.artschool.service.user;

import com.artschool.model.entity.CustomUser;
import com.artschool.model.entity.Instructor;
import com.artschool.model.entity.Student;
import com.artschool.model.form.SignUpForm;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Set;

public interface UserService {

    Student createStudent(Student student);

    Student createStudent(SignUpForm form, PasswordEncoder passwordEncoder);

    Instructor createInstructor(Instructor instructor);

    Student reinitializeStudent(Student student);

    Instructor reinitializeInstructor(Instructor instructor);

    void editStatus(CustomUser customUser, String composition);

    void saveOrUpdate(CustomUser customUser);

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
