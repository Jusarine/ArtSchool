package com.artschool.controller;

import com.artschool.model.Course;
import com.artschool.model.Student;
import com.artschool.service.course.CourseService;
import com.artschool.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/course")
@SessionAttributes("user")
public class CourseController {

    private final CourseService courseService;

    private final UserService userService;

    @Autowired
    public CourseController(CourseService courseService, UserService userService) {
        this.courseService = courseService;
        this.userService = userService;
    }

    @GetMapping("/all")
    public ModelAndView allCourses(){
        return new ModelAndView("courses", "courses", courseService.findCourses());
    }

    @GetMapping("/search")
    public ModelAndView search(@RequestParam String request){
        return new ModelAndView("courses", "courses", courseService.findCoursesByName(request));
    }

    @GetMapping("/{id}")
    public ModelAndView get(@PathVariable long id, @SessionAttribute(name = "user", required = false) Student student){
        Course course = courseService.findCourseById(id);
        ModelAndView modelAndView = new ModelAndView("/course", "course", course);
        modelAndView.addObject("instructor_name", course.getInstructor().getFirstName());
        if (student != null) {
            if (courseService.isEnrolled(student, course))
                modelAndView.addObject("enrolled", true);
        }
        return modelAndView;
    }

    @GetMapping("/enroll/{id}")
    public String enroll(@PathVariable long id, @SessionAttribute(name = "user") Student student, Model model){
        courseService.enrollInCourse(student, courseService.findCourseById(id));
        model.addAttribute("user", userService.reinitializeStudent(student));
        return "redirect:/user";
    }

    @GetMapping("/unenroll/{id}")
    public String unenroll(@PathVariable long id, @SessionAttribute(name = "user") Student student, Model model){
        courseService.unenrollFromCourse(student, courseService.findCourseById(id));
        model.addAttribute("user", userService.reinitializeStudent(student));
        return "redirect:/user";
    }

}
