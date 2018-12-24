package com.artschool.model.entity;

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
        if (o == null || getClass() != o.getClass()) return false;
        Payment that = (Payment) o;

        if (id != that.id) return false;
        if(transactionId != null ? !transactionId.equals(that.transactionId) : that.transactionId != null) return false;
        if(total != null ? !total.equals(that.total) : that.total != null) return false;
        if(date != null ? !date.equals(that.date) : that.date != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int hash = (int) id;
        hash = 31 * hash + (transactionId != null ? transactionId.hashCode() : 0);
        hash = 31 * hash + (total != null ? total.hashCode() : 0);
        hash = 31 * hash + (date != null ? date.hashCode() : 0);

        return hash;
    }
}
