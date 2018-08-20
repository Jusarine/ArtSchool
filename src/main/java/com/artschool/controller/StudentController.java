package com.artschool.controller;

import com.artschool.model.Course;
import com.artschool.model.Student;
import com.artschool.service.CourseService;
import com.artschool.service.SecurityService;
import com.artschool.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequestMapping("/student")
@SessionAttributes("user")
public class StudentController {

    @GetMapping
    public String get(){
        return "user";
    }
}
