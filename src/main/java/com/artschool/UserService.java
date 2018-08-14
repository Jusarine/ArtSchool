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
    public boolean addStudent(String firstName, String lastName, String phoneNumber, String email, String password){
        Student student = new Student(firstName, lastName, phoneNumber, email, password);
        if (findByEmail(email) != null) return false;
        studentRepository.save(student);
        return true;
    }

    @Transactional
    public boolean addInstructor(String firstName, String lastName, String phoneNumber, String email, String password){
        Instructor instructor = new Instructor(firstName, lastName, phoneNumber, email, password);
        if (findByEmail(email) != null) return false;
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
