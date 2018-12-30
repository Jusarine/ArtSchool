package com.artschool.controller.course;

import com.artschool.model.entity.Course;
import com.artschool.model.form.CourseForm;
import com.artschool.service.course.CourseService;
import com.artschool.service.course.DayService;
import com.artschool.service.course.DisciplineService;
import com.artschool.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String update(@PathVariable long id,
                         @ModelAttribute CourseForm form,
                         RedirectAttributes redirectAttributes) {
        Course course = courseService.updateCourse(id, form);
        if (course == null) {
            redirectAttributes.addAttribute("error", "Course with this name already exists!");
            return "redirect:/instructor/course/edit/" + id;
        }
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
    public String save(@ModelAttribute CourseForm form, Principal principal, RedirectAttributes redirectAttributes) {
        Course course = courseService.createCourse(form, principal.getName());
        if (course == null) {
            redirectAttributes.addAttribute("error", "Course with this name already exists!");
            return "redirect:/instructor/course/create";
        }
        return "redirect:/instructor/course/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable long id) {
        courseService.deleteCourse(id);
        return "redirect:/instructor/course/list";
    }
}
