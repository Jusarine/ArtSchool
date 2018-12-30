package com.artschool.service.course;

import com.artschool.model.entity.Photo;
import com.artschool.model.form.SearchPhotoForm;
import com.artschool.repository.PhotoRepository;
import com.artschool.service.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class PhotoServiceImpl implements PhotoService {

    private final PhotoRepository photoRepository;

    private final UserService userService;

    private final CourseService courseService;

    private boolean retain = false;

    public PhotoServiceImpl(PhotoRepository photoRepository, UserService userService, CourseService courseService) {
        this.photoRepository = photoRepository;
        this.userService = userService;
        this.courseService = courseService;
    }

    @Override
    @Transactional
    public Photo createPhoto(Photo photo) {
        photo.getAuthor().addPhoto(photo);
        photo.getCourse().addPhoto(photo);
        photoRepository.save(photo);
        return photo;
    }

    @Override
    @Transactional
    public Photo createPhoto(String authorEmail, String courseName) {
        Photo photo = new Photo(userService.findByEmail(authorEmail), courseService.findCourseByName(courseName));
        photo.getAuthor().addPhoto(photo);
        photo.getCourse().addPhoto(photo);
        photoRepository.save(photo);
        return photo;
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Photo> findPhotosByAuthorEmail(String authorEmail) {
        return photoRepository.findPhotosByAuthorEmail(authorEmail);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Photo> findPhotos(SearchPhotoForm form) {
        retain = false;
        return findAll(findByCourseId(form.getCourseId(), findByAuthorId(form.getAuthorId(), new HashSet<>())));
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Photo> findByAuthorId(Long authorId, Set<Photo> result) {
        if (authorId != null){
            retainOrAdd(result, photoRepository.findPhotosByAuthorId(authorId));
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Photo> findByCourseId(Long courseId, Set<Photo> result) {
        if (courseId != null){
            retainOrAdd(result, photoRepository.findPhotosByCourseId(courseId));
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Photo> findAll(Set<Photo> result) {
        if (!retain) result.addAll(photoRepository.findAll());
        return result;
    }

    private void retainOrAdd(Set<Photo> to, Set<Photo> from) {
        if (retain) to.retainAll(from);
        else {
            to.addAll(from);
            retain = true;
        }
    }
}
