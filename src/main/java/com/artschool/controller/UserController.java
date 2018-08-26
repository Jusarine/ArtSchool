package com.artschool.controller;

import com.artschool.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes("user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String profile(){
        return "/user/profile";
    }

    @GetMapping("/profile/{id}")
    public ModelAndView profileById(@PathVariable long id){
        return new ModelAndView("/user/profile_by_id", "member", userService.findById(id));
    }

    @GetMapping("/instructor/search")
    public ModelAndView search(@RequestParam String request){
        return new ModelAndView("/user/users", "users", userService.findByName(request));
    }

    @GetMapping("/instructors")
    public ModelAndView instructors(){
        return new ModelAndView("/user/users", "users", userService.findInstructors());
    }
}
