package com.artschool.service.course;

import com.artschool.model.entity.Payment;
import com.artschool.model.entity.Student;
import com.artschool.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    @Transactional
    public void createPayment(Payment payment){
        paymentRepository.save(payment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Payment> findPayments(Student student){
        return paymentRepository.findPaymentsByPayer(student);
    }
}
