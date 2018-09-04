package com.artschool.service.course;

import com.artschool.model.entity.Discipline;

import java.util.List;

public interface DisciplineService {

    void addDiscipline(String discipline);

    void addDiscipline(String... disciplines);

    Discipline getDiscipline(String discipline);

    List<Discipline> getDisciplines(String... disciplines);

    List<Discipline> findDisciplines();
}
