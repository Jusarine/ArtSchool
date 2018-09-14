package com.artschool.controller;

import com.artschool.model.entity.*;
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

@Controller
@RequestMapping("/course")
@SessionAttributes("user")
public class CourseController {

    private final CourseService courseService;

    private final UserService userService;

    private final DayService dayService;

    private final DisciplineService disciplineService;

    @Autowired
    public CourseController(CourseService courseService, UserService userService, DayService dayService, DisciplineService disciplineService) {
        this.courseService = courseService;
        this.userService = userService;
        this.dayService = dayService;
        this.disciplineService = disciplineService;
    }

    @GetMapping("/all")
    public ModelAndView allCourses(){
        return new ModelAndView("/course/all_courses", "courses", courseService.findCourses());
    }

    @GetMapping("/user")
    public String userCourses(){
        return "/course/user_courses";
    }

    @GetMapping("/search")
    public ModelAndView search(@RequestParam String request){
        return new ModelAndView("/course/all_courses", "courses", courseService.findCoursesByName(request));
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

    @PostMapping("/enroll/{id}")
    @ResponseBody
    public void enroll(@PathVariable long id, @SessionAttribute(name = "user") Student student){
        courseService.enrollInCourse(student, courseService.findCourseById(id));
    }

    @GetMapping("/unenroll/{id}")
    public String unenroll(@PathVariable long id, @SessionAttribute(name = "user") Student student, Model model){
        courseService.unenrollFromCourse(student, courseService.findCourseById(id));
        model.addAttribute("user", userService.reinitializeStudent(student));
        return "redirect:/course/user";
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable long id){
        ModelAndView modelAndView = new ModelAndView("/course/edit_course");
        modelAndView.addObject("course", courseService.findCourseById(id));
        modelAndView.addObject("days", dayService.findDays());
        modelAndView.addObject("disciplines", disciplineService.findDisciplines());
        return modelAndView;
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable long id,
                         @ModelAttribute CourseForm form,
                         @SessionAttribute(name = "user") CustomUser customUser,
                         Model model){

        courseService.updateCourse(id, form);
        model.addAttribute("user", userService.reinitializeInstructor((Instructor) customUser));
        return "redirect:/course/user";
    }

    @GetMapping("/create")
    public ModelAndView create(){
        ModelAndView modelAndView = new ModelAndView("/course/create_course");
        modelAndView.addObject("days", dayService.findDays());
        modelAndView.addObject("disciplines", disciplineService.findDisciplines());
        return modelAndView;
    }

    @PostMapping("/save")
    public String save(@ModelAttribute CourseForm form,
                       @SessionAttribute(name = "user") CustomUser customUser){
        courseService.createCourse(form, (Instructor) customUser);
        return "redirect:/course/user";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable long id,
                         @SessionAttribute(name = "user") CustomUser customUser,
                         Model model){
        courseService.deleteCourse(id);
        model.addAttribute("user", userService.reinitializeInstructor((Instructor)customUser));
        return "redirect:/course/user";
    }
}
