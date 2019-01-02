package com.artschool.model.form;

public class SearchPhotoForm {

    private String name;

    private Long courseId;

    private Long authorId;

    public SearchPhotoForm() {
    }

    public Long getCourseId() {
        return courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }
}
