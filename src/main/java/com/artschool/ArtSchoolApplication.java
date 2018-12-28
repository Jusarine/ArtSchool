package com.artschool;

import com.artschool.model.entity.*;
import com.artschool.model.enumeration.Audience;
import com.artschool.model.enumeration.Gender;
import com.artschool.service.course.CourseService;
import com.artschool.service.course.DateService;
import com.artschool.service.course.DayService;
import com.artschool.service.course.DisciplineService;
import com.artschool.service.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.DayOfWeek;

@SpringBootApplication
@EnableTransactionManagement
public class ArtSchoolApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArtSchoolApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(final UserService userService, final CourseService courseService, final DayService dayService, final DateService dateService, final DisciplineService disciplineService) {
        return strings -> {

            dayService.addDays(DayOfWeek.values());
            disciplineService.addDiscipline("CERAMICS", "DRAWING", "JEWELLERY", "MOSAICS", "PAINTING", "PRINTMAKING", "SCULPTURE");

            Instructor instructor1 = userService.createInstructor(new Instructor("Emily", "Horton", Gender.FEMALE,
                    "380508836472", "admin@gmail.com", "$2a$10$Xg.JmfKBVKJNR.GoM8nX8.POT3KJW5tE75ngItbj.s6vGTGDtyuXS",
                    "Emily Horton received her degree in Fine Arts from Academy of Art University in San Francisco in 2008. " +
                            "She is experienced in various fields including drawing and painting, sculpting, 2d and 3d animation and illustration. " +
                            "As a teacher, she is confident with all of the age groups from toddlers to adults, providing personal approach and fun learning environment in her classes."));
            Instructor instructor2 = userService.createInstructor(new Instructor("Mark", "Reinold", Gender.MALE,
                    "380978463742", "admin2@gmail.com", "$2a$10$Xg.JmfKBVKJNR.GoM8nX8.POT3KJW5tE75ngItbj.s6vGTGDtyuXS",
                    "Mark Reinold is a Florida born San Francisico illustrator specializing in traditional media like watercolor, ink, charcoal and acrylics. " +
                            "He graduated with a BFA from the Academy of Art University and now works as an instructor and freelance illustrator. " +
                            "Having taken an interest in art since a child, Mark pulls influences from classical art and the masters of 20th century American illustration, as well as influence from contemporary illustrators and concept artists. " +
                            "His work spans from classical studies and demonstrations of realism to highly stylized storytelling and character illustrations."));

            Course course1 = courseService.createCourse(new Course("Pen and Ink Drawing",
                    disciplineService.getDisciplines("DRAWING"),
                    Audience.TEENS,
                    20,
                    60,
                    dateService.createDate(new Date("Sep 5, 2019 - Dec 5, 2019", "12:00", "14:00")),
                    dayService.getDays(DayOfWeek.MONDAY, DayOfWeek.THURSDAY),
                    "This class is for beginners where we will learn the basic techniques of pen and ink drawing. We will be focusing on strokes, building depth, tone and value. Various exercises are used as well as learning how to properly use the equipment.",
                    instructor1));
            Course course2 = courseService.createCourse(new Course("Landscapes in Oil",
                    disciplineService.getDisciplines("PAINTING"),
                    Audience.ADULTS,
                    25,
                    40,
                    dateService.createDate(new Date("Sep 25, 2019 - Nov 5, 2019", "19:00", "21:00")),
                    dayService.getDays(DayOfWeek.TUESDAY, DayOfWeek.FRIDAY),
                    "Bring your supplies and come ready to paint! In this workshop, instructor will give group and individualized instruction including, color mixing, composition, atmospheric perspective and other things to bring your landscape to life.",
                    instructor1));
            Course course3 = courseService.createCourse(new Course("Expressive Acrylic Painting",
                    disciplineService.getDisciplines("PAINTING"),
                    Audience.KIDS,
                    15,
                    50,
                    dateService.createDate(new Date("Nov 1, 2019 - Dec 1, 2019", "16:00", "18:00")),
                    dayService.getDays(DayOfWeek.SATURDAY),
                    "Learn the art of expressive painting and rethink the process of creating art. Let you creativity guide you.  Follow your instinct and explore different mediums while enjoying this unique process.",
                    instructor2));

            Student student1 = userService.createStudent(new Student("Jack", "Ward", Gender.MALE, "380976352376", "user@gmail.com", "$2a$10$GaihOGb3ZS9I9ZdKxSKi4uXVnuYo/7Hd63qGn2l46gq0xxoeEQ8IS"));
            Student student2 = userService.createStudent(new Student("Edgar", "Cannon", Gender.MALE, "380976352376", "user2@gmail.com", "$2a$10$GaihOGb3ZS9I9ZdKxSKi4uXVnuYo/7Hd63qGn2l46gq0xxoeEQ8IS"));

            course1.addStudent(student1);
            course2.addStudent(student2);
            course3.addStudent(student1);
            course3.addStudent(student2);

            courseService.saveOrUpdate(course1);
            courseService.saveOrUpdate(course2);
            courseService.saveOrUpdate(course3);
        };
    }
}
