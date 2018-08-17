package com.artschool;

import com.artschool.model.Course;

import com.artschool.model.Instructor;

import com.artschool.model.Student;
import com.artschool.service.CourseService;

import com.artschool.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ArtSchoolApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArtSchoolApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(final UserService userService, final CourseService courseService) {
        return strings -> {
            Instructor instructor = new Instructor("Emily", "Horton", "+380508836472", "admin@gmail.com", "$2a$10$Xg.JmfKBVKJNR.GoM8nX8.POT3KJW5tE75ngItbj.s6vGTGDtyuXS");
            userService.createInstructor(instructor);
            Student student = new Student("Jack", "Ward", "+380976352376", "user@gmail.com", "$2a$10$GaihOGb3ZS9I9ZdKxSKi4uXVnuYo/7Hd63qGn2l46gq0xxoeEQ8IS");
            userService.createStudent(student);

            Course course = new Course("Pen and Ink Drawing",
                    "This class is for beginners where we will learn the basic techniques of pen and ink drawing. We will be focusing on strokes, building depth, tone and value. Various exercises are used as well as learning how to properly use the equipment.",
                    instructor);
            courseService.createCourse(course);
            courseService.enrollInCourse(student, course);
        };
    }
}
