package com.artschool.service.user;

import com.artschool.model.CustomUser;
import com.artschool.model.Instructor;
import com.artschool.model.Student;
import com.artschool.repository.InstructorRepository;
import com.artschool.repository.StudentRepository;
import org.hibernate.Hibernate;
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

    @Override
    public Student createStudent(Student student) {
        if (findByEmail(student.getEmail()) != null) return null;
        studentRepository.save(student);
        return student;
    }

    @Override
    @Transactional
    public Student createStudent(String firstName, String lastName, String phoneNumber, String email, String password){
        if (findByEmail(email) != null) return null;
        Student student = new Student(firstName, lastName, phoneNumber, email, password);
        studentRepository.save(student);
        return student;
    }

    @Override
    @Transactional
    public Instructor createInstructor(String firstName, String lastName, String phoneNumber, String email, String password){
        if (findByEmail(email) != null) return null;
        Instructor instructor = new Instructor(firstName, lastName, phoneNumber, email, password);
        instructorRepository.save(instructor);
        return instructor;
    }

    @Override
    @Transactional
    public Instructor createInstructor(Instructor instructor) {
        if(findByEmail(instructor.getEmail()) != null) return null;
        instructorRepository.save(instructor);
        return instructor;
    }

    @Override
    @Transactional
    public Student reinitializeStudent(Student student){
        Student s = findStudentById(student.getId());
        Hibernate.initialize(s.getCourses());
        return s;
    }


    @Override
    @Transactional(readOnly = true)
    public Student findStudentById(long id){
        return studentRepository.findStudentById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Instructor findInstructorById(long id){
        return instructorRepository.findInstructorById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Student findStudentByEmail(String email){
        return studentRepository.findStudentByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public Instructor findInstructorByEmail(String email){
        return instructorRepository.findInstructorByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomUser findByEmail(String email){
        CustomUser customUser = findStudentByEmail(email);
        return customUser == null ? findInstructorByEmail(email) : customUser;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> findStudents(){
        return studentRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Instructor> findInstructors(){
        return instructorRepository.findAll();
    }

}
