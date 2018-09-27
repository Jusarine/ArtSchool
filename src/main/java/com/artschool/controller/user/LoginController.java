package com.artschool.controller.user;

import com.artschool.model.entity.CustomUser;
import com.artschool.model.entity.Instructor;
import com.artschool.model.entity.PasswordResetToken;
import com.artschool.model.entity.Student;
import com.artschool.model.form.SignUpForm;
import com.artschool.service.security.EmailService;
import com.artschool.service.security.PasswordTokenService;
import com.artschool.service.security.SecurityService;
import com.artschool.service.user.UserService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    private final PasswordTokenService passwordTokenService;

    @Autowired
    public LoginController(UserService userService, SecurityService securityService, PasswordEncoder passwordEncoder,
                           EmailService emailService, PasswordTokenService passwordTokenService) {
        this.userService = userService;
        this.securityService = securityService;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.passwordTokenService = passwordTokenService;
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
    public String createUser(@ModelAttribute SignUpForm form, Model model, RedirectAttributes redirectAttributes){
        Student student = userService.createStudent(form, passwordEncoder);
        if (student != null){
            securityService.login(form.getEmail(), form.getPassword());
            model.addAttribute("user", student);
            return "redirect:/profile";
        }
        redirectAttributes.addAttribute("error", "User with this email already exists!");
        return "redirect:/login";
    }

    @GetMapping("/forgot_password")
    public String showForgotPasswordPage(){
        return "/user/forgot_password";
    }

    @PostMapping("/forgot_password")
    public String sendEmail(@RequestParam String email,
                            RedirectAttributes redirectAttributes,
                            HttpServletRequest req){
        CustomUser user = userService.findByEmail(email);
        if (user == null) {
            redirectAttributes.addAttribute("error", "There are not account associated with this email!");
            return "redirect:/forgot_password";
        }

        String token = UUID.randomUUID().toString();
        user.setResetToken(new PasswordResetToken(token, user));
        userService.saveOrUpdate(user);

        String appUrl = req.getRequestURL().toString().replace(req.getRequestURI(), req.getContextPath());
        constructResetTokenEmail(email, appUrl + "/reset_password?id=" + user.getId() + "&token=" + token);

        redirectAttributes.addAttribute("success", "Check your email for a link to reset your password.");
        return "redirect:/login";
    }

    @GetMapping("/reset_password")
    public String showResetPasswordPage(){
        return "/user/reset_password";
    }

    @PostMapping("/reset_password")
    public String resetPassword(@RequestParam long id,
                                @RequestParam String token,
                                @RequestParam String password,
                                RedirectAttributes redirectAttributes){
        PasswordResetToken passwordResetToken = passwordTokenService.findByToken(token);
        if (passwordResetToken == null || passwordResetToken.isExpired() || passwordResetToken.getUser().getId() != id) {
            redirectAttributes.addAttribute("error", "Wrong reset token!");
            return "redirect:/login";
        }

        CustomUser user = passwordResetToken.getUser();
        user.setPassword(passwordEncoder.encode(password));
        userService.saveOrUpdate(user);

        redirectAttributes.addAttribute("success", "Password was successfully restored.");
        return "redirect:/login";
    }

    private void constructResetTokenEmail(String recipient, String resetLink){
        SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
        passwordResetEmail.setTo(recipient);
        passwordResetEmail.setSubject("ArtSchool - Password Reset Request");
        passwordResetEmail.setText("To reset your password, click the link below:\n" + resetLink);
        emailService.sendEmail(passwordResetEmail);
    }
}
