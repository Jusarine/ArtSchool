package com.artschool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    InstructorRepository instructorRepository;

    @Transactional
    public boolean addStudent(String firstName, String lastName, String password, String phoneNumber, String email){
        Student student = new Student(firstName, lastName, password, phoneNumber, email);
        if (studentRepository.findByEmail(email) != null) return false;
        studentRepository.save(student);
        return true;
    }

    @Transactional
    public boolean addInstructor(String firstName, String lastName, String password, String phoneNumber, String email){
        Instructor instructor = new Instructor(firstName, lastName, password, phoneNumber, email);
        if (instructorRepository.findByEmail(email) != null) return false;
        instructorRepository.save(instructor);
        return true;
    }

    @Transactional
    public Student findStudentByEmail(String email){
        return studentRepository.findByEmail(email);
    }

    @Transactional
    public Instructor findInstructorByEmail(String email){
        return instructorRepository.findByEmail(email);
    }

    @Transactional
    public CustomUser findByEmail(String email){
        CustomUser customUser = findStudentByEmail(email);
        return customUser == null ? findInstructorByEmail(email) : customUser;
    }

    @Transactional(readOnly = true)
    public List<Student> findStudents(){
        return studentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Instructor> findInstructors(){
        return instructorRepository.findAll();
    }

}
