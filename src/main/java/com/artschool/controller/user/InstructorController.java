package com.artschool.controller.user;

import com.artschool.model.entity.Instructor;
import com.artschool.model.form.ProfileForm;
import com.artschool.service.util.LoadPhotoService;
import com.artschool.service.user.UserService;
import com.artschool.service.util.PageableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;

@Controller
public class InstructorController {

    private final UserService userService;

    private final LoadPhotoService loadPhotoService;

    private final PageableService<Instructor> instructorPageableService;

    @Autowired
    public InstructorController(UserService userService, LoadPhotoService loadPhotoService,
                                PageableService<Instructor> instructorPageableService) {
        this.userService = userService;
        this.loadPhotoService = loadPhotoService;
        this.instructorPageableService = instructorPageableService;
    }

    @GetMapping("/search/instructor")
    public ModelAndView searchInstructor(@RequestParam(required = false) String name,
                                         @RequestParam(required = false) Integer page,
                                         @RequestParam(required = false) Integer size) {
        ModelAndView modelAndView = new ModelAndView("/user/users");
        Page<Instructor> instructors;
        if (name == null || name.equals(""))
            instructors = instructorPageableService.paginate(userService.findInstructors(), page, size);
        else
            instructors = instructorPageableService.paginate(userService.findInstructorsByName(name), page, size);

        modelAndView.addObject("instructors", instructors);
        modelAndView.addObject("pages", instructors.getTotalPages());
        return modelAndView;
    }

    @GetMapping("/search/instructor/{id}")
    public ModelAndView instructorById(@PathVariable long id, Principal principal) {
        ModelAndView modelAndView = new ModelAndView("/user/profile_by_id");
        if (principal != null) {
            Instructor owner = userService.findInstructorByEmail(principal.getName());
            if (owner != null && owner.getId() == id) modelAndView.addObject("owner", true);
        }
        modelAndView.addObject("member", userService.findInstructorById(id));
        return modelAndView;
    }

    @GetMapping("/instructor/profile/edit")
    public ModelAndView editProfile(Principal principal) {
        return new ModelAndView("/user/edit_profile", "user",
                userService.findInstructorByEmail(principal.getName()));
    }

    @PostMapping("/instructor/profile/save")
    public String saveProfile(@ModelAttribute ProfileForm form,
                              @RequestParam(required = false) MultipartFile photo,
                              Principal principal,
                              RedirectAttributes redirectAttributes) throws IOException {
        Instructor instructor = userService.editInstructor(principal.getName(), form);
        if (instructor == null) {
            redirectAttributes.addAttribute("error", "User with this email already exists!");
            return "redirect:/profile/edit";
        }
        if (photo != null && !photo.isEmpty()) loadPhotoService.writeInstructorPhoto(instructor.getId(), photo);

        if (instructor.getEmail().equals(principal.getName())) return "redirect:/profile";
        else return "redirect:/logout";
    }

    @PostMapping("/instructor/edit_status")
    public @ResponseBody String editStatus(@RequestParam("value") String status, Principal principal) {
        userService.editInstructorStatus(principal.getName(), status);
        return status;
    }

    @PostMapping("/upload_photo/instructor/{id}")
    public String uploadInstructorPhoto(@PathVariable long id, @RequestParam MultipartFile photo) throws IOException {
        loadPhotoService.writeInstructorPhoto(id, photo);
        return "redirect:/instructor/profile";
    }

    @GetMapping("/get_photo/instructor/{id}")
    public ResponseEntity<byte[]> getInstructorPhoto(@PathVariable long id) throws IOException {
        return loadPhotoService.readInstructorPhoto(id);
    }

}
