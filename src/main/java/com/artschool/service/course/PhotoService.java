package com.artschool.service.course;

import com.artschool.model.entity.Photo;
import com.artschool.model.form.SearchPhotoForm;

import java.util.Set;

public interface PhotoService {

    Photo createPhoto(Photo photo);

    Photo createPhoto(String authorEmail, String courseName);

    Set<Photo> findPhotosByAuthorEmail(String authorEmail);

    Set<Photo> findPhotos(SearchPhotoForm form);

    Set<Photo> findByAuthorId(Long authorId, Set<Photo> result);

    Set<Photo> findByCourseId(Long courseId, Set<Photo> result);

    Set<Photo> findAll(Set<Photo> result);
}
