package com.artschool.controller.user;

import com.artschool.model.entity.Student;
import com.artschool.model.form.ProfileForm;
import com.artschool.service.course.CourseService;
import com.artschool.service.util.LoadPhotoService;
import com.artschool.service.user.StudentSearchService;
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
public class StudentController {

    private final UserService userService;

    private final CourseService courseService;

    private final StudentSearchService studentSearchService;

    private final LoadPhotoService loadPhotoService;

    private final PageableService<Student> studentPageableService;

    @Autowired
    public StudentController(UserService userService, CourseService courseService,
                             StudentSearchService studentSearchService, LoadPhotoService loadPhotoService,
                             PageableService<Student> studentPageableService) {
        this.userService = userService;
        this.courseService = courseService;
        this.studentSearchService = studentSearchService;
        this.loadPhotoService = loadPhotoService;
        this.studentPageableService = studentPageableService;
    }

    @GetMapping("/search/student")
    public ModelAndView searchStudent(@RequestParam(required = false) String name,
                                      @RequestParam(required = false) Integer course,
                                      @RequestParam(required = false) Integer page,
                                      @RequestParam(required = false) Integer size){
        ModelAndView modelAndView = new ModelAndView("/user/users");
        modelAndView.addObject("courses", courseService.findCourses());

        Page<Student> students = studentPageableService.paginate(studentSearchService.findStudents(name, course),
                page, size);

        modelAndView.addObject("students", students);
        modelAndView.addObject("pages", students.getTotalPages());
        return modelAndView;
    }

    @GetMapping("/search/student/{id}")
    public ModelAndView studentById(@PathVariable long id, Principal principal) {
        ModelAndView modelAndView = new ModelAndView("/user/profile_by_id");
        if (principal != null) {
            Student owner = userService.findStudentByEmail(principal.getName());
            if (owner != null && owner.getId() == id) modelAndView.addObject("owner", true);
        }
        modelAndView.addObject("member", userService.findStudentById(id));
        return modelAndView;
    }

    @GetMapping("/student/profile/edit")
    public ModelAndView editProfile(Principal principal) {
        return new ModelAndView("/user/edit_profile", "user",
                userService.findStudentByEmail(principal.getName()));
    }

    @PostMapping("/student/profile/save")
    public String saveProfile(@ModelAttribute ProfileForm form,
                              @RequestParam(required = false) MultipartFile photo,
                              Principal principal,
                              RedirectAttributes redirectAttributes) throws IOException {
        Student student = userService.editStudent(principal.getName(), form);
        if (student == null) {
            redirectAttributes.addAttribute("error", "User with this email already exists!");
            return "redirect:/student/profile/edit";
        }
        if (photo != null && !photo.isEmpty()) loadPhotoService.writeStudentPhoto(student.getId(), photo);

        if (student.getEmail().equals(principal.getName())) return "redirect:/profile";
        else return "redirect:/logout";
    }

    @PostMapping("/student/edit_status")
    public @ResponseBody String editStatus(@RequestParam("value") String status, Principal principal) {
        userService.editStudentStatus(principal.getName(), status);
        return status;
    }

    @PostMapping("/upload_photo/student/{id}")
    public String uploadStudentPhoto(@PathVariable long id, @RequestParam MultipartFile photo) throws IOException {
        loadPhotoService.writeStudentPhoto(id, photo);
        return "redirect:/student/profile";
    }

    @GetMapping("/get_photo/student/{id}")
    public ResponseEntity<byte[]> getStudentPhoto(@PathVariable long id) throws IOException {
        return loadPhotoService.readStudentPhoto(id);
    }

}
