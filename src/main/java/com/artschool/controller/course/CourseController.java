package com.artschool.controller.course;

import com.artschool.model.entity.*;
import com.artschool.model.form.SearchCourseForm;
import com.artschool.service.course.CourseService;
import com.artschool.service.course.DisciplineService;
import com.artschool.service.course.SearchService;
import com.artschool.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/course")
@SessionAttributes("user")
public class CourseController {

    private final CourseService courseService;

    private final DisciplineService disciplineService;

    private final UserService userService;

    private final SearchService searchService;

    @Autowired
    public CourseController(CourseService courseService, DisciplineService disciplineService, UserService userService, SearchService searchService) {
        this.courseService = courseService;
        this.disciplineService = disciplineService;
        this.userService = userService;
        this.searchService = searchService;
    }

    @GetMapping("/search")
    public ModelAndView search(@ModelAttribute SearchCourseForm form){
        ModelAndView modelAndView = new ModelAndView("/course/all_courses");
        modelAndView.addObject("courses", searchService.findCourses(form));
        modelAndView.addObject("disciplines", disciplineService.findDisciplines());
        modelAndView.addObject("instructors", userService.findInstructors());
        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView get(@PathVariable long id,
                            @SessionAttribute(name = "user", required = false) CustomUser customUser){
        Course course = courseService.findCourseById(id);
        ModelAndView modelAndView = new ModelAndView("/course/course", "course", course);
        if (customUser instanceof Student) {
            modelAndView.addObject("enrolled", courseService.isEnrolled((Student) customUser, course));
        }
        else if (customUser instanceof Instructor){
            modelAndView.addObject("author", courseService.isAuthor((Instructor) customUser, course));
        }
        return modelAndView;
    }
}
