package com.artschool.repository;

import com.artschool.model.entity.Payment;
import com.artschool.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findPaymentsByPayer(Student payer);
}
