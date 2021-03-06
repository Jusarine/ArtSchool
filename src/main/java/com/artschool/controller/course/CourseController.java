package com.artschool.controller.course;

import com.artschool.model.entity.*;
import com.artschool.model.form.SearchCourseForm;
import com.artschool.service.course.CourseService;
import com.artschool.service.course.DayService;
import com.artschool.service.course.DisciplineService;
import com.artschool.service.course.CourseSearchService;
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

import java.io.IOException;
import java.security.Principal;

@Controller
@RequestMapping("/course")
public class CourseController {

    private final CourseService courseService;

    private final DisciplineService disciplineService;

    private final UserService userService;

    private final CourseSearchService courseSearchService;

    private final DayService dayService;

    private final LoadPhotoService loadPhotoService;

    private final PageableService<Course> pageableService;

    @Autowired
    public CourseController(CourseService courseService, DisciplineService disciplineService, UserService userService,
                            CourseSearchService courseSearchService, DayService dayService,
                            LoadPhotoService loadPhotoService, PageableService<Course> pageableService) {
        this.courseService = courseService;
        this.disciplineService = disciplineService;
        this.userService = userService;
        this.courseSearchService = courseSearchService;
        this.dayService = dayService;
        this.loadPhotoService = loadPhotoService;
        this.pageableService = pageableService;
    }

    @GetMapping("/search")
    public ModelAndView search(@ModelAttribute SearchCourseForm form,
                               @RequestParam(required = false) Integer page,
                               @RequestParam(required = false) Integer size) {
        ModelAndView modelAndView = new ModelAndView("/course/all_courses");

        Page<Course> courses = pageableService.paginate(courseSearchService.findCourses(form), page, size);
        modelAndView.addObject("courses", courses);
        modelAndView.addObject("pages", courses.getTotalPages());
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
        } else if (customUser instanceof Instructor){
            modelAndView.addObject("author", courseService.isAuthor((Instructor) customUser, course));
        }
        return modelAndView;
    }

    @PostMapping("/upload_photo/{id}")
    public String uploadCoursePhoto(@PathVariable long id, @RequestParam MultipartFile photo) throws IOException {
        loadPhotoService.writeCoursePhoto(id, photo);
        return "redirect:/course/" + id;
    }

    @GetMapping("/get_photo/{id}")
    public ResponseEntity<byte[]> getCoursePhoto(@PathVariable long id) throws IOException {
        return loadPhotoService.readCoursePhoto(id);
    }

    private void initSearchParameters(ModelAndView modelAndView) {
        modelAndView.addObject("disciplines", disciplineService.findDisciplines());
        modelAndView.addObject("instructors", userService.findInstructors());
        modelAndView.addObject("days", dayService.findDays());
        modelAndView.addObject("fromFee", courseService.findMinCourseFee());
        modelAndView.addObject("toFee", courseService.findMaxCourseFee());
    }
}
