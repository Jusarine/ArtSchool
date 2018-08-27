package com.artschool.controller;

import com.artschool.model.*;
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

    @GetMapping("/enroll/{id}")
    public String enroll(@PathVariable long id, @SessionAttribute(name = "user") Student student){
        courseService.enrollInCourse(student, courseService.findCourseById(id));
        return "redirect:/course/user";
    }

    @GetMapping("/unenroll/{id}")
    public String unenroll(@PathVariable long id, @SessionAttribute(name = "user") Student student, Model model){
        courseService.unenrollFromCourse(student, courseService.findCourseById(id));
        model.addAttribute("user", userService.reinitializeStudent(student));
        return "redirect:/course/user";
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable long id){
        return new ModelAndView("/course/edit_course", "course", courseService.findCourseById(id));
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable long id,
                         @RequestParam String name,
                         @RequestParam Discipline discipline,
                         @RequestParam Audience audience,
                         @RequestParam String description,
                         @SessionAttribute(name = "user") CustomUser customUser,
                         Model model){
        courseService.updateCourse(id, name, discipline, audience, description);
        model.addAttribute("user", userService.reinitializeInstructor((Instructor)customUser));
        return "redirect:/course/user";
    }

    @GetMapping("/create")
    public String create(){
        return "/course/create_course";
    }

    @PostMapping("/save")
    public String save(@RequestParam String name,
                       @RequestParam Discipline discipline,
                       @RequestParam Audience audience,
                       @RequestParam String description,
                       @SessionAttribute(name = "user") CustomUser customUser){
        courseService.createCourse(name, discipline, audience, description, (Instructor) customUser);
        return "redirect:/course/user";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable long id,
                         @SessionAttribute(name = "user") CustomUser customUser,
                         Model model){
        courseService.deleteCourse(courseService.findCourseById(id));
        model.addAttribute("user", userService.reinitializeInstructor((Instructor)customUser));
        return "redirect:/course/user";
    }
}
