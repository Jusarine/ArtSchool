package com.artschool.service.util;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface LoadPhotoService {

    void writeInstructorPhoto(long photoId, MultipartFile photo) throws IOException;

    void writeStudentPhoto(long photoId, MultipartFile photo) throws IOException;

    void writeCoursePhoto(long photoId, MultipartFile photo) throws IOException;

    void writeGalleryPhoto(long photoId, MultipartFile photo) throws IOException;

    ResponseEntity<byte[]> readInstructorPhoto(long photoId) throws IOException;

    ResponseEntity<byte[]> readStudentPhoto(long photoId) throws IOException;

    ResponseEntity<byte[]> readCoursePhoto(long photoId) throws IOException;

    ResponseEntity<byte[]> readGalleryPhoto(long photoId) throws IOException;

    void deleteCoursePhoto(long photoId) throws IOException;
}
