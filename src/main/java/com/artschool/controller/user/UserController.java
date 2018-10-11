package com.artschool.controller.user;

import com.artschool.model.entity.CustomUser;
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
    public ModelAndView profileById(@PathVariable long id,
                                    @SessionAttribute(name = "user", required = false) CustomUser customUser){
        ModelAndView modelAndView = new ModelAndView("/user/profile_by_id");
        if (customUser == null || customUser.getId() != id) modelAndView.addObject("another", true);
        modelAndView.addObject("member", userService.findById(id));
        return modelAndView;
    }

    @GetMapping("/search/instructor")
    public ModelAndView search(@RequestParam String request){
        return new ModelAndView("/user/users", "users", userService.findInstructorsByName(request));
    }

    @GetMapping("/instructors")
    public ModelAndView instructors(){
        return new ModelAndView("/user/users", "users", userService.findInstructors());
    }

    @PostMapping("/edit_status")
    public @ResponseBody String editStatus(@RequestParam("value") String status,
                                           @SessionAttribute(name = "user") CustomUser customUser){
        userService.editStatus(customUser, status);
        return status;
    }
}
