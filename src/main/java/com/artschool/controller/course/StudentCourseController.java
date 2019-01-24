package com.artschool.controller.course;

import com.artschool.model.entity.*;
import com.artschool.service.course.CourseService;
import com.artschool.service.course.PaymentService;
import com.artschool.service.user.UserService;
import com.artschool.service.util.PageableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/student/course")
public class StudentCourseController {

    private final CourseService courseService;

    private final UserService userService;

    private final PaymentService paymentService;

    private final PageableService<Course> coursePageableService;

    private final PageableService<Payment> paymentPageableService;

    @Autowired
    public StudentCourseController(CourseService courseService, UserService userService,
                                   PaymentService paymentService, PageableService<Course> coursePageableService,
                                   PageableService<Payment> paymentPageableService) {
        this.courseService = courseService;
        this.userService = userService;
        this.paymentService = paymentService;
        this.coursePageableService = coursePageableService;
        this.paymentPageableService = paymentPageableService;
    }

    @GetMapping("/list")
    public ModelAndView userCourses(Principal principal,
                                    @RequestParam(required = false) Integer page,
                                    @RequestParam(required = false) Integer size) {
        ModelAndView modelAndView = new ModelAndView("/course/user_courses");
        Page<Course> courses = coursePageableService.paginate(userService.getStudentCourses(principal.getName()), page, size);
        modelAndView.addObject("courses", courses);
        modelAndView.addObject("pages", courses.getTotalPages());
        return modelAndView;
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
        List<Payment> payments = paymentService.findPayments(principal.getName(), id);
        if (payments != null && !payments.isEmpty()) {
            courseService.enrollInCourse(principal.getName(), id);
            return "redirect:/student/course/list";
        }
        redirectAttributes.addAttribute("error", "You haven't made a payment for this course! " +
                "If you want to enroll, please pay for it by PayPal.");
        return "redirect:/course/" + id;
    }

    @GetMapping("/payments")
    public ModelAndView payments(Principal principal,
                                 @RequestParam(required = false) Integer page,
                                 @RequestParam(required = false) Integer size) {
        ModelAndView modelAndView = new ModelAndView("/course/user_payments");
        Page<Payment> payments = paymentPageableService.paginate(paymentService.findPayments(principal.getName()),
                page, size);
        modelAndView.addObject("payments", payments);
        modelAndView.addObject("pages", payments.getTotalPages());
        return modelAndView;
    }
}
