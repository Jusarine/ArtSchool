package com.artschool.controller;

import com.artschool.model.entity.Instructor;
import com.artschool.model.entity.Student;
import com.artschool.model.form.SignUpForm;
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
    public String createUser(@ModelAttribute SignUpForm form, Model model){
        Student student = userService.createStudent(form, passwordEncoder);
        if (student != null){
            securityService.login(form.getEmail(), form.getPassword());
            model.addAttribute("user", student);
            return "redirect:/profile";
        }
        return "redirect:/login";
    }
}
