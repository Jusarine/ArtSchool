package com.artschool.service.util;

import com.artschool.model.entity.CustomUser;
import com.artschool.model.enumeration.Gender;
import com.artschool.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class LoadPhotoServiceImpl implements LoadPhotoService {

    private static final String PHOTO_EXTENSION = ".png";

    private static final String INSTRUCTOR_IMAGES_PATH = "images/users/instructors/";
    private static final String STUDENT_IMAGES_PATH = "images/users/students/";
    private static final String COURSE_IMAGES_PATH = "images/courses/";
    private static final String GALLERY_IMAGES_PATH = "images/gallery/";

    private static final String GIRL_IMAGE_PATH = "src/main/resources/static/images/girl.png";
    private static final String BOY_IMAGE_PATH = "src/main/resources/static/images/boy.png";
    private static final String COURSE_IMAGE_PATH = "src/main/resources/static/images/course.png";

    private final UserService userService;

    @Autowired
    public LoadPhotoServiceImpl(UserService userService) {
        this.userService = userService;
    }

    private void writePhoto(String path, MultipartFile photo) throws IOException {
        Files.write(Paths.get(path), photo.getBytes());
    }

    @Override
    public void writeInstructorPhoto(long photoId, MultipartFile photo) throws IOException {
        writePhoto(INSTRUCTOR_IMAGES_PATH + photoId + PHOTO_EXTENSION, photo);
    }

    @Override
    public void writeStudentPhoto(long photoId, MultipartFile photo) throws IOException {
        writePhoto(STUDENT_IMAGES_PATH + photoId + PHOTO_EXTENSION, photo);
    }

    @Override
    public void writeCoursePhoto(long photoId, MultipartFile photo) throws IOException {
        writePhoto(COURSE_IMAGES_PATH + photoId + PHOTO_EXTENSION, photo);
    }

    @Override
    public void writeGalleryPhoto(long photoId, MultipartFile photo) throws IOException {
        writePhoto(GALLERY_IMAGES_PATH + photoId + PHOTO_EXTENSION, photo);
    }

    private ResponseEntity<byte[]> readPhoto(String mainPath, String sparePath) throws IOException {
        Path path = Paths.get(mainPath);
        if (Files.notExists(path)) {
            path = Paths.get(sparePath);
        }
        byte[] photo = Files.readAllBytes(path);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(photo, headers, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<byte[]> readCoursePhoto(long photoId) throws IOException {
        return readPhoto(COURSE_IMAGES_PATH + photoId + PHOTO_EXTENSION,
                COURSE_IMAGE_PATH);
    }

    @Override
    public ResponseEntity<byte[]> readGalleryPhoto(long photoId) throws IOException {
        return readPhoto(GALLERY_IMAGES_PATH + photoId + PHOTO_EXTENSION,
                COURSE_IMAGE_PATH);
    }

    private ResponseEntity<byte[]> readPhoto(Path path) throws IOException {
        byte[] photo = Files.readAllBytes(path);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(photo, headers, HttpStatus.OK);
    }

    private Path getUserSparePath(CustomUser user) {
        return Paths.get(user.getGender().equals(Gender.FEMALE) ? GIRL_IMAGE_PATH : BOY_IMAGE_PATH);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<byte[]> readInstructorPhoto(long photoId) throws IOException {
        Path path = Paths.get(INSTRUCTOR_IMAGES_PATH + photoId + PHOTO_EXTENSION);
        if (Files.notExists(path)) {
            path = getUserSparePath(userService.findInstructorById(photoId));
        }
        return readPhoto(path);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<byte[]> readStudentPhoto(long photoId) throws IOException {
        Path path = Paths.get(STUDENT_IMAGES_PATH + photoId + PHOTO_EXTENSION);
        if (Files.notExists(path)) {
            path = getUserSparePath(userService.findStudentById(photoId));
        }
        return readPhoto(path);
    }

    private void deletePhoto(String path) throws IOException {
        Files.deleteIfExists(Paths.get(path));
    }

    @Override
    public void deleteCoursePhoto(long photoId) throws IOException {
        deletePhoto(COURSE_IMAGES_PATH + photoId + PHOTO_EXTENSION);
    }
}
