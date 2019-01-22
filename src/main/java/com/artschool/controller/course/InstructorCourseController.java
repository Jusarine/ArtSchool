package com.artschool.controller.course;

import com.artschool.model.entity.Course;
import com.artschool.model.form.CourseForm;
import com.artschool.service.course.CourseService;
import com.artschool.service.course.DayService;
import com.artschool.service.course.DisciplineService;
import com.artschool.service.gallery.LoadPhotoService;
import com.artschool.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequestMapping("/instructor/course")
public class InstructorCourseController {

    private final CourseService courseService;

    private final UserService userService;

    private final DayService dayService;

    private final DisciplineService disciplineService;

    private final LoadPhotoService loadPhotoService;

    @Autowired
    public InstructorCourseController(CourseService courseService, UserService userService, DayService dayService,
                                      DisciplineService disciplineService, LoadPhotoService loadPhotoService) {
        this.courseService = courseService;
        this.userService = userService;
        this.dayService = dayService;
        this.disciplineService = disciplineService;
        this.loadPhotoService = loadPhotoService;
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
                         @RequestParam(required = false) MultipartFile photo,
                         RedirectAttributes redirectAttributes) throws IOException {
        Course course = courseService.updateCourse(id, form);
        if (course == null) {
            redirectAttributes.addAttribute("error", "Course with this name already exists!");
            return "redirect:/instructor/course/edit/" + id;
        }
        if (photo != null && !photo.isEmpty()) loadPhotoService.writeCoursePhoto(id, photo);
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
    public String save(@ModelAttribute CourseForm form,
                       @RequestParam MultipartFile photo,
                       Principal principal,
                       RedirectAttributes redirectAttributes) throws IOException {
        Course course = courseService.createCourse(form, principal.getName());
        if (course == null) {
            redirectAttributes.addAttribute("error", "Course with this name already exists!");
            return "redirect:/instructor/course/create";
        }
        loadPhotoService.writeCoursePhoto(course.getId(), photo);
        return "redirect:/instructor/course/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable long id) {
        courseService.deleteCourse(id);
        return "redirect:/instructor/course/list";
    }
}
