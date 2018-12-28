package com.artschool.service.course;

import com.artschool.model.entity.Payment;

import java.util.List;

public interface PaymentService {

    void createPayment(Payment payment);

    List<Payment> findPayments(String payerEmail);

    List<Payment> findPayments(long productId);

    List<Payment> findPayments(String payerEmail, long productId);
}
