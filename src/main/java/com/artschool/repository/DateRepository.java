package com.artschool.repository;

import com.artschool.model.entity.Date;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DateRepository extends JpaRepository<Date, Long> {
}
