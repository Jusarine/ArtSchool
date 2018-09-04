package com.artschool.repository;

import com.artschool.model.entity.Discipline;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisciplineRepository extends JpaRepository<Discipline, Long> {

    Discipline findDisciplineByName(String name);
}
