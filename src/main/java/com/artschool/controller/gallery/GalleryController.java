package com.artschool.controller.gallery;

import com.artschool.model.entity.Photo;
import com.artschool.model.form.SearchPhotoForm;
import com.artschool.service.course.CourseService;
import com.artschool.service.util.LoadPhotoService;
import com.artschool.service.gallery.PhotoService;
import com.artschool.service.user.UserService;
import com.artschool.service.util.PageableService;
import org.springframework.data.domain.Page;
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

    private final PageableService<Photo> pageableService;

    public GalleryController(PhotoService photoService, UserService userService, CourseService courseService,
                             LoadPhotoService loadPhotoService, PageableService<Photo> pageableService) {
        this.photoService = photoService;
        this.userService = userService;
        this.courseService = courseService;
        this.loadPhotoService = loadPhotoService;
        this.pageableService = pageableService;
    }

    @GetMapping
    public String searchPhoto(@ModelAttribute SearchPhotoForm form,
                              @RequestParam(required = false) Integer page,
                              @RequestParam(required = false) Integer size,
                              Model model) {
        Page<Photo> photos = pageableService.paginate(photoService.findPhotos(form), page, size);
        model.addAttribute("photos", photos);
        model.addAttribute("pages", photos.getTotalPages());

        model.addAttribute("authors", userService.findUsers());
        model.addAttribute("courses", courseService.findCourses());
        return "/gallery/gallery";
    }

    @GetMapping("/user")
    public String userPhoto(Principal principal,
                            @RequestParam(required = false) Integer page,
                            @RequestParam(required = false) Integer size,
                            Model model) {
        Page<Photo> photos = pageableService.paginate(photoService.findByAuthorEmail(principal.getName()), page, size);
        model.addAttribute("photos", photos);
        model.addAttribute("pages", photos.getTotalPages());

        model.addAttribute("authors", userService.findUsers());
        model.addAttribute("courses", courseService.findCourses());
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
