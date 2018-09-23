package com.artschool.controller.course;

import com.artschool.model.entity.*;
import com.artschool.service.course.CourseService;
import com.artschool.service.course.PaymentService;
import com.artschool.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;

@Controller
@RequestMapping("/student/course")
@SessionAttributes("user")
public class StudentCourseController {

    private final CourseService courseService;

    private final UserService userService;

    private final PaymentService paymentService;

    @Autowired
    public StudentCourseController(CourseService courseService, UserService userService, PaymentService paymentService) {
        this.courseService = courseService;
        this.userService = userService;
        this.paymentService = paymentService;
    }

    @GetMapping("/list")
    public String userCourses(){
        return "/course/user_courses";
    }


    @PostMapping("/enroll/{id}")
    @ResponseBody
    public void enroll(@PathVariable long id, @RequestParam String transactionId, @SessionAttribute(name = "user") Student student){
        Course course = courseService.findCourseById(id);
        paymentService.createPayment(new Payment(transactionId, student, course, course.getFee(), LocalDate.now()));
        courseService.enrollInCourse(student, course);
    }

    @GetMapping("/unenroll/{id}")
    public String unenroll(@PathVariable long id, @SessionAttribute(name = "user") Student student, Model model){
        courseService.unenrollFromCourse(student, courseService.findCourseById(id));
        model.addAttribute("user", userService.reinitializeStudent(student));
        return "redirect:/student/course/list";
    }

    @GetMapping("/restore/{id}")
    public String restore(@PathVariable long id, @SessionAttribute(name = "user") Student student){
        Course course = courseService.findCourseById(id);
        if (paymentService.findPayments(course) != null){
            courseService.enrollInCourse(student, course);
            return "redirect:/student/course/list";
        }
        return "redirect:/course/" + id;
    }

    @GetMapping("/payments")
    public ModelAndView payments(@SessionAttribute(name = "user") Student student){
        return new ModelAndView("/course/user_payments", "payments", paymentService.findPayments(student));
    }
}
