package com.artschool.service.course;

import com.artschool.model.entity.Course;
import com.artschool.model.entity.Payment;
import com.artschool.model.entity.Student;

import java.util.List;

public interface PaymentService {
    void createPayment(Payment payment);

    List<Payment> findPayments(Student student);

    Payment findPayments(Course product);
}
