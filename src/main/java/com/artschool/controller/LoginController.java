package com.artschool.controller;

import com.artschool.model.*;
import com.artschool.service.CourseService;
import com.artschool.service.SecurityService;
import com.artschool.service.UserService;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Controller
@SessionAttributes("user")
public class LoginController {

    private final UserService userService;

    private final CourseService courseService;

    private final SecurityService securityService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public LoginController(UserService userService, CourseService courseService, SecurityService securityService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.courseService = courseService;
        this.securityService = securityService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/authorized")
    public String authorize(Model model, Principal principal){

        Student student = userService.findStudentByEmail(principal.getName());
        if (student != null){
            Hibernate.initialize(student.getCourses());
            model.addAttribute("user", student);
            System.err.println("controller" + student.getCourses());
            return "redirect:/student";
        }
        Instructor instructor = userService.findInstructorByEmail(principal.getName());
        if (instructor != null){
            Hibernate.initialize(instructor.getCourses());
            model.addAttribute("user", instructor);
            return "redirect:/instructor";
        }
        return "redirect:/login";
    }

    @GetMapping("/all_courses")
    public ModelAndView allCourses(){
        return new ModelAndView("all_courses", "all_courses", courseService.findCourses());
    }

    @PostMapping(value = "/new_user")
    public String createUser(@RequestParam("first_name") String firstName,
                               @RequestParam("last_name") String lastName,
                               @RequestParam("phone_number") String phoneNumber,
                               @RequestParam("email") String email,
                               @RequestParam("password") String password,
                               Model model){

        String encodedPassword = passwordEncoder.encode(password);
        Student student = userService.createStudent(firstName, lastName, phoneNumber, email, encodedPassword);

        if (student != null){
            securityService.login(email, password);
            model.addAttribute("user", student);
            return "redirect:/student";
        }
        return "redirect:/login";
    }
}
