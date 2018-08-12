package com.artschool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyController {

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/authorized")
    public String authorize(Model model){
        User user = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        CustomUser customUser = userService.findByEmail(user.getUsername());
        if (customUser == null)
            throw new UsernameNotFoundException("User with email " + user.getUsername() + " not found");

        model.addAttribute("first_name", customUser.getFirstName());

        if (customUser.getRole().equals(UserRole.ADMIN)) return "instructor";
        else return "student";
    }
}
