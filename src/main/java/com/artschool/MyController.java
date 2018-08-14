package com.artschool;

import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class MyController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
        model.addAttribute("first_name", customUser.getFirstName());

        if (customUser.getRole().equals(UserRole.ADMIN)) return "instructor";
        else return "student";
    }

    @RequestMapping("/student")
    public String student(@RequestParam("first_name") String firstName, Model model){
        model.addAttribute("first_name", firstName);
        return "student";
    }

    @RequestMapping(value = "/new_user", method = RequestMethod.POST)
    public ModelAndView createUser(@RequestParam("first_name") String firstName,
                           @RequestParam("last_name") String lastName,
                           @RequestParam("phone_number") String phoneNumber,
                           @RequestParam("email") String email,
                           @RequestParam("password") String password,
                           ModelMap model){

        String encodedPassword = passwordEncoder.encode(password);

        if (userService.addStudent(firstName, lastName, phoneNumber, email, encodedPassword)){
            model.addAttribute("first_name", firstName);
            return new ModelAndView("redirect:/student", model);
        }
        return new ModelAndView("login");
    }
}
