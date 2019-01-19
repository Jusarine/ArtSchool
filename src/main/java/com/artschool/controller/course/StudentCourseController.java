package com.artschool.controller.course;

import com.artschool.model.entity.*;
import com.artschool.service.course.CourseService;
import com.artschool.service.course.PaymentService;
import com.artschool.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDate;

@Controller
@RequestMapping("/student/course")
public class StudentCourseController {

    private final CourseService courseService;

    private final UserService userService;

    private final PaymentService paymentService;

    @Autowired
    public StudentCourseController(CourseService courseService, UserService userService,
                                   PaymentService paymentService) {
        this.courseService = courseService;
        this.userService = userService;
        this.paymentService = paymentService;
    }

    @GetMapping("/list")
    public ModelAndView userCourses(Principal principal) {
        return new ModelAndView("/course/user_courses", "courses",
                userService.getStudentCourses(principal.getName()));
    }

    @PostMapping("/enroll/{id}")
    @ResponseBody
    public void enroll(@PathVariable long id, @RequestParam String transactionId, Principal principal) {
        Course course = courseService.findCourseById(id);
        Student student = userService.findStudentByEmail(principal.getName());
        paymentService.createPayment(new Payment(transactionId, student, course, course.getFee(), LocalDate.now()));
        courseService.enrollInCourse(principal.getName(), id);
    }

    @GetMapping("/unenroll/{id}")
    public String unenroll(@PathVariable long id, Principal principal) {
        courseService.unenrollFromCourse(principal.getName(), id);
        return "redirect:/student/course/list";
    }

    @GetMapping("/restore/{id}")
    public String restore(@PathVariable long id,
                          Principal principal,
                          RedirectAttributes redirectAttributes) {
        if (paymentService.findPayments(principal.getName(), id) != null) {
            courseService.enrollInCourse(principal.getName(), id);
            return "redirect:/student/course/list";
        }
        redirectAttributes.addAttribute("error", "You haven't made a payment for this course! " +
                "If you want to enroll, please pay for it by PayPal.");
        return "redirect:/course/" + id;
    }

    @GetMapping("/payments")
    public ModelAndView payments(Principal principal) {
        return new ModelAndView("/course/user_payments", "payments",
                paymentService.findPayments(principal.getName()));
    }
}
