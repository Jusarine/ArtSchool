package com.artschool;

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
    public CommandLineRunner demo(final UserService userService) {
        return strings -> {
            userService.addInstructor("Emily", "Horton", "$2a$10$Xg.JmfKBVKJNR.GoM8nX8.POT3KJW5tE75ngItbj.s6vGTGDtyuXS", "+380508836472", "admin@gmail.com");
            userService.addStudent("Jack", "Ward", "$2a$10$GaihOGb3ZS9I9ZdKxSKi4uXVnuYo/7Hd63qGn2l46gq0xxoeEQ8IS", "+380976352376", "user@gmail.com");
        };
    }

}
