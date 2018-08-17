package com.artschool.service;

import com.artschool.model.CustomUser;
import com.artschool.model.Instructor;
import com.artschool.model.Student;
import com.artschool.repository.InstructorRepository;
import com.artschool.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private final StudentRepository studentRepository;

    private final InstructorRepository instructorRepository;

    @Autowired
    public UserServiceImpl(StudentRepository studentRepository, InstructorRepository instructorRepository) {
        this.studentRepository = studentRepository;
        this.instructorRepository = instructorRepository;
    }

    @Transactional
    @Override
    public Student createStudent(String firstName, String lastName, String phoneNumber, String email, String password){
        if (findByEmail(email) != null) return null;
        Student student = new Student(firstName, lastName, phoneNumber, email, password);
        studentRepository.save(student);
        return student;
    }

    @Override
    public Student createStudent(Student student) {
        if (findByEmail(student.getEmail()) != null) return null;
        studentRepository.save(student);
        return student;
    }

    @Transactional
    @Override
    public Instructor createInstructor(String firstName, String lastName, String phoneNumber, String email, String password){
        if (findByEmail(email) != null) return null;
        Instructor instructor = new Instructor(firstName, lastName, phoneNumber, email, password);
        instructorRepository.save(instructor);
        return instructor;
    }

    @Override
    public Instructor createInstructor(Instructor instructor) {
        if(findByEmail(instructor.getEmail()) != null) return null;
        instructorRepository.save(instructor);
        return instructor;
    }

    @Transactional(readOnly = true)
    @Override
    public Student findStudentByEmail(String email){
        return studentRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    @Override
    public Instructor findInstructorByEmail(String email){
        return instructorRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    @Override
    public CustomUser findByEmail(String email){
        CustomUser customUser = findStudentByEmail(email);
        return customUser == null ? findInstructorByEmail(email) : customUser;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Student> findStudents(){
        return studentRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Instructor> findInstructors(){
        return instructorRepository.findAll();
    }

}
