package com.artschool.service.user;

import com.artschool.model.entity.CustomUser;
import com.artschool.model.enumeration.Gender;
import com.artschool.model.entity.Instructor;
import com.artschool.model.entity.Student;
import com.artschool.model.form.SignUpForm;
import com.artschool.repository.InstructorRepository;
import com.artschool.repository.StudentRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService{

    private final StudentRepository studentRepository;

    private final InstructorRepository instructorRepository;

    @Autowired
    public UserServiceImpl(StudentRepository studentRepository, InstructorRepository instructorRepository/*, PasswordEncoder passwordEncoder*/) {
        this.studentRepository = studentRepository;
        this.instructorRepository = instructorRepository;
    }

    @Override
    @Transactional
    public Student createStudent(Student student) {
        if (findByEmail(student.getEmail()) != null) return null;
        studentRepository.save(student);
        return student;
    }

    @Override
    @Transactional
    public Student createStudent(SignUpForm form, PasswordEncoder passwordEncoder) {
        if (findByEmail(form.getEmail()) != null) return null;
        String encodedPassword = passwordEncoder.encode(form.getPassword());
        Student student = new Student(form.getFirstName(), form.getLastName(), Gender.valueOf(form.getGender()),
                form.getPhoneNumber(), form.getEmail(), encodedPassword);
        studentRepository.save(student);
        return student;
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
    @Transactional
    public Instructor reinitializeInstructor(Instructor instructor){
        Instructor i = findInstructorById(instructor.getId());
        Hibernate.initialize(i.getCourses());
        return i;
    }

    @Override
    @Transactional
    public void editStatus(CustomUser customUser, String status){
        if (status.equals("")) status = null;
        customUser.setStatus(status);
        saveOrUpdate(customUser);
    }

    @Override
    @Transactional
    public void saveOrUpdate(CustomUser customUser) {
        if (customUser instanceof Student) studentRepository.save((Student) customUser);
        else if (customUser instanceof Instructor) instructorRepository.save((Instructor) customUser);
    }

    @Override
    @Transactional(readOnly = true)
    public Student findStudentById(long id){
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Instructor findInstructorById(long id){
        return instructorRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomUser findById(long id){
        CustomUser customUser = findStudentById(id);
        return customUser == null ? findInstructorById(id) : customUser;
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
    public Student findStudentByResetToken(String resetToken) {
        return studentRepository.findStudentByResetToken(resetToken);
    }

    @Override
    @Transactional(readOnly = true)
    public Instructor findInstructorByResetToken(String resetToken) {
        return instructorRepository.findInstructorByResetToken(resetToken);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomUser findByResetToken(String resetToken){
        CustomUser customUser = findStudentByResetToken(resetToken);
        return customUser == null ? findInstructorByResetToken(resetToken) : customUser;
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Instructor> findByName(String name) {
        Set<Instructor> set = new HashSet<>();
        String[] words = name.split("[\\s]+");
        if (words.length == 0){
            set.addAll(instructorRepository.findAll());
        }
        else if (words.length == 1){
            set.addAll(instructorRepository.findInstructorsByFirstNameContaining(words[0]));
            set.addAll(instructorRepository.findInstructorsByLastNameContaining(words[0]));
        }
        else if (words.length == 2){
            set.addAll(instructorRepository.findInstructorsByFirstNameContainingAndLastNameContaining(words[0], words[1]));
            set.addAll(instructorRepository.findInstructorsByFirstNameContainingAndLastNameContaining(words[1], words[0]));
        }
        return set;
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
