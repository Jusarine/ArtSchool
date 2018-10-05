package com.artschool.controller.course;

import com.artschool.model.entity.*;
import com.artschool.model.enumeration.Audience;
import com.artschool.service.course.CourseService;
import com.artschool.service.course.DisciplineService;
import com.artschool.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/course")
@SessionAttributes("user")
public class CourseController {

    private final CourseService courseService;

    private final DisciplineService disciplineService;

    private final UserService userService;

    @Autowired
    public CourseController(CourseService courseService, DisciplineService disciplineService, UserService userService) {
        this.courseService = courseService;
        this.disciplineService = disciplineService;
        this.userService = userService;
    }

    @GetMapping("/search")
    public ModelAndView search(@RequestParam(required = false) String request,
                               @RequestParam(required = false) String discipline,
                               @RequestParam(required = false) Audience audience,
                               @RequestParam(required = false) String instructor){

        ModelAndView modelAndView = new ModelAndView("/course/all_courses");
        Set<Course> courses = new HashSet<>();
        boolean retain = false;

        if (request != null && !"".equals(request)){
            courses.addAll(courseService.findCoursesByName(request));
            retain = true;
        }
        if (discipline != null && !"".equals(discipline)){
            retainOrAdd(courses, courseService.findCoursesByDiscipline(disciplineService.getDiscipline(discipline)), retain);
            retain = true;
        }
        if (audience != null){
            retainOrAdd(courses, courseService.findCoursesByAudience(audience), retain);
            retain = true;
        }
        if (instructor != null && !"".equals(instructor)){
            retainOrAdd(courses, courseService.findCoursesByInstructor(userService.findInstructorByName(instructor)), retain);
            retain = true;
        }
        if (!retain) courses.addAll(courseService.findCourses());

        modelAndView.addObject("courses", courses);
        modelAndView.addObject("disciplines", disciplineService.findDisciplines());
        modelAndView.addObject("instructors", userService.findInstructors());
        return modelAndView;
    }

    private void retainOrAdd(Set<Course> c1, Set<Course> c2, boolean retain){
        if (retain) c1.retainAll(c2);
        else c1.addAll(c2);
    }

    @GetMapping("/{id}")
    public ModelAndView get(@PathVariable long id, @SessionAttribute(name = "user", required = false) CustomUser customUser){
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
