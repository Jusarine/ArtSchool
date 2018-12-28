package com.artschool.controller.user;

import com.artschool.service.course.CourseService;
import com.artschool.service.user.StudentSearchService;
import com.artschool.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class UserController {

    private final UserService userService;

    private final CourseService courseService;

    private final StudentSearchService studentSearchService;

    @Autowired
    public UserController(UserService userService, CourseService courseService, StudentSearchService studentSearchService) {
        this.userService = userService;
        this.courseService = courseService;
        this.studentSearchService = studentSearchService;
    }

    @GetMapping("/profile")
    public ModelAndView profile(Principal principal) {
        ModelAndView modelAndView = new ModelAndView("/user/profile");
        modelAndView.addObject("user", userService.findByEmail(principal.getName()));
        modelAndView.addObject("owner", true);
        return modelAndView;
    }

    @GetMapping("/profile/{id}")
    public ModelAndView profileById(@PathVariable long id, Principal principal) {
        ModelAndView modelAndView = new ModelAndView("/user/profile_by_id");
        if (principal != null && userService.findByEmail(principal.getName()).getId() == id)
                modelAndView.addObject("owner", true);
        modelAndView.addObject("member", userService.findById(id));
        return modelAndView;
    }

    @GetMapping("/search/instructor")
    public ModelAndView searchInstructor(@RequestParam(required = false) String request) {
        ModelAndView modelAndView = new ModelAndView("/user/users");
        if (request == null)  modelAndView.addObject("instructors", userService.findInstructors());
        else modelAndView.addObject("instructors", userService.findInstructorsByName(request));
        return modelAndView;
    }

    @GetMapping("/search/student")
    public ModelAndView searchStudent(@RequestParam(required = false) String request,
                                      @RequestParam(required = false) Integer course){
        ModelAndView modelAndView = new ModelAndView("/user/users");
        modelAndView.addObject("courses", courseService.findCourses());
        modelAndView.addObject("students", studentSearchService.findStudents(request, course));
        return modelAndView;
    }

    @PostMapping("/edit_status")
    public @ResponseBody String editStatus(@RequestParam("value") String status, Principal principal) {
        userService.editStatus(principal.getName(), status);
        return status;
    }
}
