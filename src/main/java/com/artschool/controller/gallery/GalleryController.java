package com.artschool.controller.gallery;

import com.artschool.model.entity.Photo;
import com.artschool.model.form.SearchPhotoForm;
import com.artschool.service.course.CourseService;
import com.artschool.service.gallery.LoadPhotoService;
import com.artschool.service.gallery.PhotoService;
import com.artschool.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequestMapping("/gallery")
public class GalleryController {

    private final PhotoService photoService;

    private final UserService userService;

    private final CourseService courseService;

    private final LoadPhotoService loadPhotoService;

    public GalleryController(PhotoService photoService, UserService userService, CourseService courseService,
                             LoadPhotoService loadPhotoService) {
        this.photoService = photoService;
        this.userService = userService;
        this.courseService = courseService;
        this.loadPhotoService = loadPhotoService;
    }

    @GetMapping
    public String searchPhoto(@ModelAttribute SearchPhotoForm form,
                              Model model) {
        model.addAttribute("authors", userService.findUsers());
        model.addAttribute("courses", courseService.findCourses());
        model.addAttribute("photos", photoService.findPhotos(form));
        return "/gallery/gallery";
    }

    @GetMapping("/user")
    public String userPhoto(Principal principal,
                              Model model) {
        model.addAttribute("authors", userService.findUsers());
        model.addAttribute("courses", courseService.findCourses());
        model.addAttribute("photos", photoService.findByAuthorEmail(principal.getName()));
        return "/gallery/user_gallery";
    }

    @GetMapping("/add_photo")
    public String showAddPhotoPage(Model model) {
        model.addAttribute("courses", courseService.findCourses());
        return "/gallery/add_photo";
    }

    @PostMapping("/upload_photo")
    public String addPhoto(@RequestParam String name,
                           @RequestParam String course,
                           @RequestParam MultipartFile photo,
                           Principal principal) throws IOException {
        Photo p = photoService.createPhoto(name, principal.getName(), course);
        loadPhotoService.writeGalleryPhoto(p.getId(), photo);
        return "redirect:/gallery/user";
    }

    @GetMapping("/get_photo/{id}")
    public ResponseEntity<byte[]> getPhoto(@PathVariable long id) throws IOException {
        return loadPhotoService.readGalleryPhoto(id);
    }
}
