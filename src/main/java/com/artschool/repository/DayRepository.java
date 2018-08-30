package com.artschool.repository;

import com.artschool.model.entity.Day;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;

public interface DayRepository extends JpaRepository<Day, Long> {

    Day findDayByName(DayOfWeek name);
}
