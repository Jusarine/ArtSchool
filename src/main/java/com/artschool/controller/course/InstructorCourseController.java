package com.artschool.controller.course;

import com.artschool.model.form.CourseForm;
import com.artschool.service.course.CourseService;
import com.artschool.service.course.DayService;
import com.artschool.service.course.DisciplineService;
import com.artschool.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
@RequestMapping("/instructor/course")
public class InstructorCourseController {

    private final CourseService courseService;

    private final UserService userService;

    private final DayService dayService;

    private final DisciplineService disciplineService;

    @Autowired
    public InstructorCourseController(CourseService courseService, UserService userService, DayService dayService,
                                      DisciplineService disciplineService) {
        this.courseService = courseService;
        this.userService = userService;
        this.dayService = dayService;
        this.disciplineService = disciplineService;
    }

    @GetMapping("/list")
    public ModelAndView userCourses(Principal principal) {
        return new ModelAndView("/course/user_courses", "courses",
                userService.getInstructorCourses(principal.getName()));
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable long id) {
        ModelAndView modelAndView = new ModelAndView("/course/edit_course");
        modelAndView.addObject("course", courseService.findCourseByIdAndInit(id));
        modelAndView.addObject("days", dayService.findDays());
        modelAndView.addObject("disciplines", disciplineService.findDisciplines());
        return modelAndView;
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable long id, @ModelAttribute CourseForm form) {
        courseService.updateCourse(id, form);
        return "redirect:/instructor/course/list";
    }

    @GetMapping("/create")
    public ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView("/course/create_course");
        modelAndView.addObject("days", dayService.findDays());
        modelAndView.addObject("disciplines", disciplineService.findDisciplines());
        return modelAndView;
    }

    @PostMapping("/save")
    public String save(@ModelAttribute CourseForm form, Principal principal) {
        courseService.createCourse(form, principal.getName());
        return "redirect:/instructor/course/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable long id) {
        courseService.deleteCourse(id);
        return "redirect:/instructor/course/list";
    }
}
