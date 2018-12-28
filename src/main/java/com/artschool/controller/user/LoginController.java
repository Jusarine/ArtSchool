package com.artschool.controller.user;

import com.artschool.model.entity.CustomUser;
import com.artschool.model.entity.Instructor;
import com.artschool.model.entity.PasswordResetToken;
import com.artschool.model.entity.Student;
import com.artschool.model.form.SignUpStudentForm;
import com.artschool.model.form.SignUpInstructorForm;
import com.artschool.service.security.EmailService;
import com.artschool.service.security.PasswordTokenService;
import com.artschool.service.security.SecurityService;
import com.artschool.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
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
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "/user/login";
    }

    @PostMapping("/new_user")
    public String createUser(@ModelAttribute SignUpStudentForm form, RedirectAttributes redirectAttributes) {
        Student student = userService.createStudent(form, passwordEncoder);
        if (student != null){
            securityService.login(form.getEmail(), form.getPassword());
            return "redirect:/profile";
        }
        redirectAttributes.addAttribute("error", "Student with this email already exists!");
        return "redirect:/login";
    }

    @GetMapping("/instructor/create")
    public String createInstructor() {
        return "/user/create_instructor";
    }

    @PostMapping("/instructor/create")
    public String showCreateInstructorPage(@ModelAttribute SignUpInstructorForm form,
                                           RedirectAttributes redirectAttributes) {
        Instructor instructor = userService.createInstructor(form, passwordEncoder);
        if (instructor == null) {
            redirectAttributes.addAttribute("error", "Instructor with this email already exists!");
            return "redirect:/instructor/create";
        }
        return "redirect:/profile";
    }

    @GetMapping("/forgot_password")
    public String showForgotPasswordPage() {
        return "/user/forgot_password";
    }

    @PostMapping("/forgot_password")
    public String sendEmail(@RequestParam String email,
                            RedirectAttributes redirectAttributes,
                            HttpServletRequest req) {
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
    public String showResetPasswordPage() {
        return "/user/reset_password";
    }

    @PostMapping("/reset_password")
    public String resetPassword(@RequestParam long id,
                                @RequestParam String token,
                                @RequestParam String password,
                                RedirectAttributes redirectAttributes) {
        PasswordResetToken resetToken = passwordTokenService.findByToken(token);
        if (resetToken == null || resetToken.isExpired() || resetToken.getUser().getId() != id) {
            redirectAttributes.addAttribute("error", "Wrong reset token!");
            return "redirect:/login";
        }

        CustomUser user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(password));
        userService.saveOrUpdate(user);

        redirectAttributes.addAttribute("success", "Password was successfully restored.");
        return "redirect:/login";
    }

    private void constructResetTokenEmail(String recipient, String resetLink) {
        SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
        passwordResetEmail.setTo(recipient);
        passwordResetEmail.setSubject("ArtSchool - Password Reset Request");
        passwordResetEmail.setText("To reset your password, click the link below:\n" + resetLink);
        emailService.sendEmail(passwordResetEmail);
    }
}
