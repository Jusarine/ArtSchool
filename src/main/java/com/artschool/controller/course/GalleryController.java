package com.artschool.controller.course;

import com.artschool.model.entity.Photo;
import com.artschool.model.form.SearchPhotoForm;
import com.artschool.service.course.CourseService;
import com.artschool.service.course.PhotoService;
import com.artschool.service.user.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;

@Controller
@RequestMapping("/gallery")
public class GalleryController {

    private final PhotoService photoService;

    private final UserService userService;

    private final CourseService courseService;


    public GalleryController(PhotoService photoService, UserService userService, CourseService courseService) {
        this.photoService = photoService;
        this.userService = userService;
        this.courseService = courseService;
    }

    @GetMapping
    public String searchPhoto(@ModelAttribute SearchPhotoForm form,
                              Model model) {
        model.addAttribute("authors", userService.findUsers());
        model.addAttribute("courses", courseService.findCourses());
        model.addAttribute("photos", photoService.findPhotos(form));
        return "/course/gallery";
    }

    @GetMapping("/user")
    public String userPhoto(Principal principal,
                              Model model) {
        model.addAttribute("authors", userService.findUsers());
        model.addAttribute("courses", courseService.findCourses());
        model.addAttribute("photos", photoService.findPhotosByAuthorEmail(principal.getName()));
        return "/course/user_gallery";
    }

    @GetMapping("/add_photo")
    public String showAddPhotoPage(Model model) {
        model.addAttribute("courses", courseService.findCourses());
        return "/course/add_photo";
    }

    @PostMapping("/upload_photo")
    public String addPhoto(@RequestParam String course,
                           @RequestParam MultipartFile photo,
                           Principal principal) throws IOException {
        Photo p = photoService.createPhoto(principal.getName(), course);
        Files.write(Paths.get("images/photos/" + p.getId() + ".png"), photo.getBytes());
        return "redirect:/gallery/user";
    }

    @GetMapping("/get_photo/{id}")
    public ResponseEntity<byte[]> getPhoto(@PathVariable long id) throws IOException {
        Path path = Paths.get("images/photos/" + id + ".png");
        if (Files.notExists(path)) {
            path = Paths.get("src/main/resources/static/images/course.png");
        }
        byte[] photo = Files.readAllBytes(path);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(photo, headers, HttpStatus.OK);
    }
}
