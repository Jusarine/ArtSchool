package com.artschool.service.gallery;

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

    private static final String USER_IMAGES_PATH = "images/users/";
    private static final String COURSE_IMAGES_PATH = "images/courses/";
    private static final String GALLERY_IMAGES_PATH = "images/photos/";

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
    public void writeUserPhoto(long photoId, MultipartFile photo) throws IOException {
        writePhoto(USER_IMAGES_PATH + photoId + PHOTO_EXTENSION, photo);
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
    @Transactional(readOnly = true)
    public ResponseEntity<byte[]> readUserPhoto(long photoId) throws IOException {
        String mainPath = USER_IMAGES_PATH + photoId + PHOTO_EXTENSION;
        String sparePath;
        if (userService.findById(photoId).getGender().equals(Gender.FEMALE))
            sparePath = GIRL_IMAGE_PATH;
        else
            sparePath = BOY_IMAGE_PATH;

        return readPhoto(mainPath, sparePath);
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
}
