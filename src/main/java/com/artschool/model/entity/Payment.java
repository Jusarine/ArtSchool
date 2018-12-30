package com.artschool.model.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Payment {

    @Id
    @Column(name = "payment_id")
    @GeneratedValue
    private long id;

    @Column(name = "transaction_id", nullable = false)
    private String transactionId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "student_id")
    private Student payer;

    @ManyToOne(optional = false)
    @JoinColumn(name = "course_id")
    private Course product;

    private Integer total;

    private LocalDate date;

    public Payment() {
    }

    public Payment(String transactionId, Student payer, Course product, Integer total, LocalDate date) {
        this.transactionId = transactionId;
        this.payer = payer;
        this.product = product;
        this.total = total;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Student getPayer() {
        return payer;
    }

    public void setPayer(Student payer) {
        this.payer = payer;
    }

    public Course getProduct() {
        return product;
    }

    public void setProduct(Course product) {
        this.product = product;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", transactionId='" + transactionId + '\'' +
                ", total=" + total +
                ", date=" + date +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Payment)) return false;
        Payment payment = (Payment) o;

        return new EqualsBuilder()
                .append(transactionId, payment.transactionId)
                .append(payer, payment.payer)
                .append(product, payment.product)
                .append(total, payment.total)
                .append(date, payment.date)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(transactionId)
                .append(payer)
                .append(product)
                .append(total)
                .append(date)
                .toHashCode();
    }
}
