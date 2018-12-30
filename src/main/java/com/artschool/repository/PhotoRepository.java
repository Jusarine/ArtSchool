package com.artschool.repository;

import com.artschool.model.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface PhotoRepository extends JpaRepository<Photo, Long> {

    Set<Photo> findPhotosByAuthorId(long authorId);

    Set<Photo> findPhotosByCourseId(long courseId);

    Set<Photo> findPhotosByAuthorEmail(String authorEmail);
}
