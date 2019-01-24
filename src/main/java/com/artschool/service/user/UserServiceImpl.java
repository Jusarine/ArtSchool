package com.artschool.service.user;

import com.artschool.model.entity.*;
import com.artschool.model.enumeration.Gender;
import com.artschool.model.form.ProfileForm;
import com.artschool.model.form.SignUpStudentForm;
import com.artschool.model.form.SignUpInstructorForm;
import com.artschool.repository.CustomUserRepository;
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
public class UserServiceImpl implements UserService {

    private final StudentRepository studentRepository;

    private final InstructorRepository instructorRepository;

    private final CustomUserRepository customUserRepository;

    @Autowired
    public UserServiceImpl(StudentRepository studentRepository, InstructorRepository instructorRepository,
                           CustomUserRepository customUserRepository) {
        this.studentRepository = studentRepository;
        this.instructorRepository = instructorRepository;
        this.customUserRepository = customUserRepository;
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
    public Student createStudent(SignUpStudentForm form, PasswordEncoder passwordEncoder) {
        if (findByEmail(form.getEmail()) != null) return null;
        String encodedPassword = passwordEncoder.encode(form.getPassword());
        Student student = new Student(form.getFirstName(), form.getLastName(), Gender.valueOf(form.getGender()),
                form.getPhoneNumber(), form.getEmail(), encodedPassword);
        studentRepository.save(student);
        return student;
    }

    @Override
    @Transactional
    public Student editStudent(String studentEmail, ProfileForm form) {
        if (!studentEmail.equals(form.getEmail()) && findByEmail(form.getEmail()) != null) return null;
        Student student = studentRepository.findStudentByEmail(studentEmail);
        student.setFirstName(form.getFirstName());
        student.setLastName(form.getLastName());
        student.setPhoneNumber(form.getPhoneNumber());
        student.setEmail(form.getEmail());
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
    public Instructor createInstructor(SignUpInstructorForm form, PasswordEncoder passwordEncoder) {
        if (findByEmail(form.getEmail()) != null) return null;
        String encodedPassword = passwordEncoder.encode(form.getPassword());
        Instructor instructor = new Instructor(form.getFirstName(), form.getLastName(),
                Gender.valueOf(form.getGender()), form.getPhoneNumber(), form.getEmail(), encodedPassword,
                form.getBio());
        instructorRepository.save(instructor);
        return instructor;
    }

    @Override
    @Transactional
    public Instructor editInstructor(String instructorEmail, ProfileForm form) {
        if (!instructorEmail.equals(form.getEmail()) && findByEmail(form.getEmail()) != null) return null;
        Instructor instructor = instructorRepository.findInstructorByEmail(instructorEmail);
        instructor.setFirstName(form.getFirstName());
        instructor.setLastName(form.getLastName());
        instructor.setPhoneNumber(form.getPhoneNumber());
        instructor.setEmail(form.getEmail());
        instructor.setBio(form.getBio());
        return instructor;
    }

    @Override
    @Transactional
    public void editInstructorStatus(String instructorEmail, String status) {
        if (status.equals("")) status = null;
        Instructor instructor = findInstructorByEmail(instructorEmail);
        instructor.setStatus(status);
        saveOrUpdate(instructor);
    }

    @Override
    @Transactional
    public void editStudentStatus(String studentEmail, String status) {
        if (status.equals("")) status = null;
        Student student = findStudentByEmail(studentEmail);
        student.setStatus(status);
        saveOrUpdate(student);
    }

    @Override
    @Transactional
    public void saveOrUpdate(CustomUser customUser) {
        if (customUser instanceof Student) studentRepository.save((Student) customUser);
        else if (customUser instanceof Instructor) instructorRepository.save((Instructor) customUser);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Course> getStudentCourses(String studentEmail) {
        Student student = findStudentByEmail(studentEmail);
        Hibernate.initialize(student.getCourses());
        return student.getCourses();
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Course> getInstructorCourses(String instructorEmail) {
        Instructor instructor = findInstructorByEmail(instructorEmail);
        Hibernate.initialize(instructor.getCourses());
        return instructor.getCourses();
    }

    @Override
    @Transactional(readOnly = true)
    public CustomUser findInstructorById(long id) {
        return instructorRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomUser findStudentById(long id) {
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomUser findById(long id) {
        return customUserRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Student findStudentByEmail(String email) {
        return studentRepository.findStudentByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public Instructor findInstructorByEmail(String email) {
        return instructorRepository.findInstructorByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomUser findByEmail(String email) {
        return customUserRepository.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomUser findByResetToken(String resetToken) {
        return customUserRepository.findByPasswordResetToken_Token(resetToken);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Student> findStudentsByName(String name) {
        Set<Student> set = new HashSet<>();
        String[] words = name.split("[\\s]+");
        if (words.length == 0) {
            set.addAll(studentRepository.findAll());
        }
        else if (words.length == 1) {
            set.addAll(studentRepository.findStudentsByFirstNameContaining(words[0]));
            set.addAll(studentRepository.findStudentsByLastNameContaining(words[0]));
        }
        else if (words.length == 2) {
            set.addAll(studentRepository.findStudentsByFirstNameContainingAndLastNameContaining(words[0], words[1]));
            set.addAll(studentRepository.findStudentsByFirstNameContainingAndLastNameContaining(words[1], words[0]));
        }
        else {
            for (String word : words) {
                set.addAll(studentRepository.findStudentsByFirstNameContaining(word));
                set.addAll(studentRepository.findStudentsByLastNameContaining(word));
            }
        }
        return set;
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Instructor> findInstructorsByName(String name) {
        Set<Instructor> set = new HashSet<>();
        String[] words = name.split("[\\s]+");
        if (words.length == 0) {
            set.addAll(instructorRepository.findAll());
        }
        else if (words.length == 1) {
            set.addAll(instructorRepository.findInstructorsByFirstNameContaining(words[0]));
            set.addAll(instructorRepository.findInstructorsByLastNameContaining(words[0]));
        }
        else if (words.length == 2) {
            set.addAll(instructorRepository.findInstructorsByFirstNameContainingAndLastNameContaining(words[0],
                    words[1]));
            set.addAll(instructorRepository.findInstructorsByFirstNameContainingAndLastNameContaining(words[1],
                    words[0]));
        }
        else {
            for (String word : words) {
                set.addAll(instructorRepository.findInstructorsByFirstNameContaining(word));
                set.addAll(instructorRepository.findInstructorsByLastNameContaining(word));
            }
        }
        return set;
    }

    @Override
    @Transactional(readOnly = true)
    public Student findStudentByName(String name) {
        String[] words = name.split("[\\s]+");
        return studentRepository.findStudentByFirstNameAndLastName(words[0], words[1]);
    }

    @Override
    @Transactional(readOnly = true)
    public Instructor findInstructorByName(String name) {
        String[] words = name.split("[\\s]+");
        return instructorRepository.findInstructorByFirstNameAndLastName(words[0], words[1]);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> findStudents() {
        return studentRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Instructor> findInstructors() {
        return instructorRepository.findAll();
    }

    @Override
    @Transactional
    public List<CustomUser> findUsers() {
        return customUserRepository.findAll();
    }
}
