package com.artschool.service.gallery;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface LoadPhotoService {

    void writeUserPhoto(long photoId, MultipartFile photo) throws IOException;

    void writeCoursePhoto(long photoId, MultipartFile photo) throws IOException;

    void writeGalleryPhoto(long photoId, MultipartFile photo) throws IOException;

    ResponseEntity<byte[]> readUserPhoto(long photoId) throws IOException;

    ResponseEntity<byte[]> readCoursePhoto(long photoId) throws IOException;

    ResponseEntity<byte[]> readGalleryPhoto(long photoId) throws IOException;
}
