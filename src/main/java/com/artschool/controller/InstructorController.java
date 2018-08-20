package com.artschool.controller;

import com.artschool.service.CourseService;
import com.artschool.service.SecurityService;
import com.artschool.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/instructor")
@SessionAttributes("user")
public class InstructorController {

    @GetMapping
    public String get(){
        return "user";
    }
}
