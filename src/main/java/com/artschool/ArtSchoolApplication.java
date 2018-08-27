package com.artschool;

import com.artschool.model.*;
import com.artschool.service.course.CourseService;
import com.artschool.service.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class ArtSchoolApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArtSchoolApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(final UserService userService, final CourseService courseService) {
        return strings -> {

            Instructor instructor1 = userService.createInstructor("Emily", "Horton", Gender.FEMALE, "380508836472", "admin@gmail.com", "$2a$10$Xg.JmfKBVKJNR.GoM8nX8.POT3KJW5tE75ngItbj.s6vGTGDtyuXS");
            Instructor instructor2 = userService.createInstructor("Mark", "Reinold", Gender.MALE, "380978463742", "admin2@gmail.com", "$2a$10$Xg.JmfKBVKJNR.GoM8nX8.POT3KJW5tE75ngItbj.s6vGTGDtyuXS");

            Course course1 = courseService.createCourse("Pen and Ink Drawing",
                    Discipline.Drawing,
                    Audience.Teens,
                    "This class is for beginners where we will learn the basic techniques of pen and ink drawing. We will be focusing on strokes, building depth, tone and value. Various exercises are used as well as learning how to properly use the equipment.",
                    instructor1);
            Course course2 = courseService.createCourse("Landscapes in Oil",
                    Discipline.Painting,
                    Audience.Adults,
                    "Bring your supplies and come ready to paint! In this workshop, instructor will give group and individualized instruction including, color mixing, composition, atmospheric perspective and other things to bring your landscape to life.",
                    instructor1);
            Course course3 = courseService.createCourse("Expressive Acrylic Painting",
                    Discipline.Painting,
                    Audience.Kids,
                    "Learn the art of expressive painting and rethink the process of creating art. Let you creativity guide you.  Follow your instinct and explore different mediums while enjoying this unique process.",
                    instructor2);

            Student student1 = userService.createStudent("Jack", "Ward", Gender.MALE, "380976352376", "user@gmail.com", "$2a$10$GaihOGb3ZS9I9ZdKxSKi4uXVnuYo/7Hd63qGn2l46gq0xxoeEQ8IS");
            Student student2 = userService.createStudent("Edgar", "Cannon", Gender.MALE, "380976352376", "user2@gmail.com", "$2a$10$GaihOGb3ZS9I9ZdKxSKi4uXVnuYo/7Hd63qGn2l46gq0xxoeEQ8IS");

            courseService.enrollInCourse(student1, course1);
            courseService.enrollInCourse(student2, course2);
            courseService.enrollInCourse(student1, course3);
        };
    }
}
