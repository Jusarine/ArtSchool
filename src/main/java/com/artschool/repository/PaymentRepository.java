package com.artschool.repository;

import com.artschool.model.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findPaymentsByPayerEmail(String payerEmail);

    List<Payment> findPaymentsByProductId(long productId);

    List<Payment> findPaymentsByPayerEmailAndProductId(String payerEmail, long productId);
}
