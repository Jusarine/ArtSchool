package com.artschool.service.course;

import com.artschool.model.entity.Course;
import com.artschool.model.entity.Discipline;
import com.artschool.model.enumeration.Audience;
import com.artschool.model.form.SearchCourseForm;
import com.artschool.service.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.Set;

@Service
public class SearchServiceImpl implements SearchService {

    private final CourseService courseService;

    private final UserService userService;

    private final DisciplineService disciplineService;

    private final DayService dayService;

    private boolean retain = false;

    public SearchServiceImpl(CourseService courseService, UserService userService, DisciplineService disciplineService, DayService dayService) {
        this.courseService = courseService;
        this.userService = userService;
        this.disciplineService = disciplineService;
        this.dayService = dayService;
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Course> findCourses(SearchCourseForm form){
        retain = false;
        return findAll(findByDay(form.getDay(), findByFee(form.getFromFee(), form.getToFee(),
                findByInstructor(form.getInstructor(), findByAudience(form.getAudience(),
                        findByDiscipline(form.getDiscipline(), findByRequest(form.getRequest(), new HashSet<>())))))));
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Course> findByRequest(String request, Set<Course> result){
        if (request != null && !"".equals(request)){
            retainOrAdd(result, courseService.findCoursesByName(request));
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Course> findByDiscipline(String discipline, Set<Course> result){
        Discipline d = disciplineService.getDiscipline(discipline);
        if (d != null){
            retainOrAdd(result, courseService.findCoursesByDiscipline(d));
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Course> findByAudience(Audience audience, Set<Course> result){
        if (audience != null){
            retainOrAdd(result, courseService.findCoursesByAudience(audience));
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Course> findByInstructor(String instructor, Set<Course> result){
        if (instructor != null && !"".equals(instructor)){
            retainOrAdd(result, courseService.findCoursesByInstructor(userService.findInstructorByName(instructor)));
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Course> findByFee(Integer fromFee, Integer toFee, Set<Course> result){
        if (fromFee != null && toFee != null) retainOrAdd(result, courseService.findCoursesByFeeBetween(fromFee, toFee));
        else if (toFee != null) retainOrAdd(result, courseService.findCoursesByFeeBefore(toFee));
        else if (fromFee != null) retainOrAdd(result, courseService.findCoursesByFeeAfter(fromFee));
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Course> findByDay(DayOfWeek day, Set<Course> result){
        if (day != null) retainOrAdd(result, courseService.findCoursesByDay(dayService.getDay(day)));
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Course> findAll(Set<Course> result){
        if (!retain) result.addAll(courseService.findCourses());
        return result;
    }

    private void retainOrAdd(Set<Course> to, Set<Course> from){
        if (retain) to.retainAll(from);
        else {
            to.addAll(from);
            retain = true;
        }
    }
}