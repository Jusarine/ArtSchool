package com.artschool.controller.user;

import com.artschool.model.entity.CustomUser;
import com.artschool.model.entity.Instructor;
import com.artschool.model.entity.Student;
import com.artschool.model.form.SignUpForm;
import com.artschool.service.security.EmailService;
import com.artschool.service.security.SecurityService;
import com.artschool.service.user.UserService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.UUID;

@Controller
@SessionAttributes("user")
public class LoginController {

    private final UserService userService;

    private final SecurityService securityService;

    private final PasswordEncoder passwordEncoder;

    private final EmailService emailService;

    @Autowired
    public LoginController(UserService userService, SecurityService securityService, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userService = userService;
        this.securityService = securityService;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/forgot_password")
    public String showForgotPasswordPage(){
        return "/user/forgot_password";
    }

    @PostMapping("/forgot_password")
    public String sendEmail(@RequestParam String email, HttpServletRequest req){
        CustomUser user = userService.findByEmail(email);
        if (user == null) return "redirect:/login";
        user.setResetToken(UUID.randomUUID().toString());
        userService.saveOrUpdate(user);

        String appUrl = req.getRequestURL().toString().replace(req.getRequestURI(), req.getContextPath());

        SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
        passwordResetEmail.setFrom("artschool128@gmail.com");
        passwordResetEmail.setTo(email);
        passwordResetEmail.setSubject("ArtSchool - Password Reset Request");
        passwordResetEmail.setText("To reset your password, click the link below:\n" + appUrl
                + "/reset_password?token=" + user.getResetToken());
        emailService.sendEmail(passwordResetEmail);

        return "redirect:/login";
    }

    @GetMapping("/reset_password")
    public String showResetPasswordPage(){
        return "/user/reset_password";
    }

    @PostMapping("/reset_password")
    public String resetPassword(@RequestParam String token, @RequestParam String password){
        CustomUser user = userService.findByResetToken(token);
        if (user == null) return "redirect:/login";

        user.setPassword(passwordEncoder.encode(password));
        user.setResetToken(null);
        userService.saveOrUpdate(user);

        return "redirect:/login";
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
