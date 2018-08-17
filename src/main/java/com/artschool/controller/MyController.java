package com.artschool.controller;

import com.artschool.model.CustomUser;
import com.artschool.model.Student;
import com.artschool.service.SecurityService;
import com.artschool.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@Controller
@SessionAttributes("user")
public class MyController {

    private final UserService userService;

    private final SecurityService securityService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MyController(UserService userService, SecurityService securityService, PasswordEncoder passwordEncoder) {
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
        return "login";
    }

    @GetMapping("/user")
    public String user(){
        return "user";
    }

    @GetMapping("/authorized")
    public String authorize(Model model, Principal principal){
        CustomUser customUser = userService.findByEmail(principal.getName());
        model.addAttribute("user", customUser);
        return "redirect:/user";
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
            return "redirect:/user";
        }
        return "redirect:/login";
    }
}
