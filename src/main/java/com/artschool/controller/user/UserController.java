package com.artschool.controller.user;

import com.artschool.model.entity.CustomUser;
import com.artschool.service.course.CourseService;
import com.artschool.service.user.UserSearchService;
import com.artschool.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes("user")
public class UserController {

    private final UserService userService;

    private final CourseService courseService;

    private final UserSearchService userSearchService;

    @Autowired
    public UserController(UserService userService, CourseService courseService, UserSearchService userSearchService) {
        this.userService = userService;
        this.courseService = courseService;
        this.userSearchService = userSearchService;
    }

    @GetMapping("/profile")
    public String profile(){
        return "/user/profile";
    }

    @GetMapping("/profile/{id}")
    public ModelAndView profileById(@PathVariable long id,
                                    @SessionAttribute(name = "user", required = false) CustomUser customUser){
        ModelAndView modelAndView = new ModelAndView("/user/profile_by_id");
        if (customUser == null || customUser.getId() != id) modelAndView.addObject("another", true);
        modelAndView.addObject("member", userService.findById(id));
        return modelAndView;
    }

    @GetMapping("/search/instructor")
    public ModelAndView searchInstructor(@RequestParam(required = false) String request){
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
        modelAndView.addObject("students", userSearchService.findStudents(request, course));
        return modelAndView;
    }

    @PostMapping("/edit_status")
    public @ResponseBody String editStatus(@RequestParam("value") String status,
                                           @SessionAttribute(name = "user") CustomUser customUser){
        userService.editStatus(customUser, status);
        return status;
    }
}
