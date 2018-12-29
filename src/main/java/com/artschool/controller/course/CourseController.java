package com.artschool.controller.course;

import com.artschool.model.entity.*;
import com.artschool.model.form.SearchCourseForm;
import com.artschool.service.course.CourseService;
import com.artschool.service.course.DayService;
import com.artschool.service.course.DisciplineService;
import com.artschool.service.course.CourseSearchService;
import com.artschool.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;

@Controller
@RequestMapping("/course")
public class CourseController {

    private final CourseService courseService;

    private final DisciplineService disciplineService;

    private final UserService userService;

    private final CourseSearchService courseSearchService;

    private final DayService dayService;

    @Autowired
    public CourseController(CourseService courseService, DisciplineService disciplineService, UserService userService,
                            CourseSearchService courseSearchService, DayService dayService) {
        this.courseService = courseService;
        this.disciplineService = disciplineService;
        this.userService = userService;
        this.courseSearchService = courseSearchService;
        this.dayService = dayService;
    }

    @GetMapping("/search")
    public ModelAndView search(@ModelAttribute SearchCourseForm form) {
        ModelAndView modelAndView = new ModelAndView("/course/all_courses");
        modelAndView.addObject("courses", courseSearchService.findCourses(form));
        initSearchParameters(modelAndView);
        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView get(@PathVariable long id, Principal principal) {
        Course course = courseService.findCourseByIdAndInit(id);
        ModelAndView modelAndView = new ModelAndView("/course/course", "course", course);
        initSearchParameters(modelAndView);
        if (principal == null) return modelAndView;

        CustomUser customUser = userService.findByEmail(principal.getName());
        if (customUser instanceof Student) {
            modelAndView.addObject("enrolled", courseService.isEnrolled((Student) customUser, course));
        }
        else if (customUser instanceof Instructor){
            modelAndView.addObject("author", courseService.isAuthor((Instructor) customUser, course));
        }
        return modelAndView;
    }

    private void initSearchParameters(ModelAndView modelAndView) {
        modelAndView.addObject("disciplines", disciplineService.findDisciplines());
        modelAndView.addObject("instructors", userService.findInstructors());
        modelAndView.addObject("days", dayService.findDays());
        modelAndView.addObject("fromFee", courseService.findMinCourseFee());
        modelAndView.addObject("toFee", courseService.findMaxCourseFee());
    }

    @PostMapping("/upload_photo/{id}")
    public String uploadCoursePhoto(@PathVariable long id, @RequestParam MultipartFile photo) throws IOException {
        Files.write(Paths.get("images/courses/" + id + ".png"), photo.getBytes());
        return "redirect:/course/" + id;
    }

    @GetMapping("/get_photo/{id}")
    public ResponseEntity<byte[]> getCoursePhoto(@PathVariable long id) throws IOException {
        Path path = Paths.get("images/courses/" + id + ".png");
        if (Files.notExists(path)) {
            path = Paths.get("src/main/resources/static/images/course.png");
        }
        byte[] photo = Files.readAllBytes(path);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(photo, headers, HttpStatus.OK);
    }
}
