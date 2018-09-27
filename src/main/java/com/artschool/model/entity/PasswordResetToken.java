package com.artschool.model.entity;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
public class PasswordResetToken {

    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String token;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", nullable = false)
    private CustomUser user;

    private Date expiryDate;

    public PasswordResetToken() {
    }

    public PasswordResetToken(String token, CustomUser user) {
        this.token = token;
        this.user = user;
        setExpiryDate();
    }

    public static int getEXPIRATION() {
        return EXPIRATION;
    }

    public long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public CustomUser getUser() {
        return user;
    }

    public void setUser(CustomUser user) {
        this.user = user;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate){
        this.expiryDate = expiryDate;
    }

    private void setExpiryDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, EXPIRATION);
        this.expiryDate = cal.getTime();
    }

    public boolean isExpired(){
        return new Date().after(this.expiryDate);
    }
}
