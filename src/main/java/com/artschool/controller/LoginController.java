package com.artschool.controller;

import com.artschool.model.*;
import com.artschool.service.security.SecurityService;
import com.artschool.service.user.UserService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@SessionAttributes("user")
public class LoginController {

    private final UserService userService;

    private final SecurityService securityService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public LoginController(UserService userService, SecurityService securityService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.securityService = securityService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "/user/login";
    }

    @GetMapping("/authorized")
    public String authorize(Model model, Principal principal){

        Student student = userService.findStudentByEmail(principal.getName());
        if (student != null){
            Hibernate.initialize(student.getCourses());
            model.addAttribute("user", student);
        }
        Instructor instructor = userService.findInstructorByEmail(principal.getName());
        if (instructor != null){
            Hibernate.initialize(instructor.getCourses());
            model.addAttribute("user", instructor);
        }
        return "redirect:/profile";
    }

    @PostMapping("/new_user")
    public String createUser(@RequestParam("first_name") String firstName,
                               @RequestParam("last_name") String lastName,
                               @RequestParam("gender") String gender,
                               @RequestParam("phone_number") String phoneNumber,
                               @RequestParam("email") String email,
                               @RequestParam("password") String password,
                               Model model){

        String encodedPassword = passwordEncoder.encode(password);
        Student student = userService.createStudent(firstName, lastName, Gender.valueOf(gender), phoneNumber, email, encodedPassword);

        if (student != null){
            securityService.login(email, password);
            model.addAttribute("user", student);
            return "redirect:/profile";
        }
        return "redirect:/login";
    }
}
