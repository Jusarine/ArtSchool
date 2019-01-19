package com.artschool.model.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Embeddable
public class PasswordResetToken {

    private static final int EXPIRATION = 60 * 24;

    @Column(name = "prt")
    private String token;

    @Column(name = "prt_expiry_date")
    private Date expiryDate;

    public PasswordResetToken() {
    }

    public PasswordResetToken(String token) {
        this.token = token;
        setExpiryDate();
    }

    public static int getEXPIRATION() {
        return EXPIRATION;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    private void setExpiryDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, EXPIRATION);
        this.expiryDate = cal.getTime();
    }

    public boolean isExpired() {
        return new Date().after(this.expiryDate);
    }

    @Override
    public String toString() {
        return "PasswordResetToken{" +
                "token='" + token + '\'' +
                ", expiryDate=" + expiryDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PasswordResetToken)) return false;
        PasswordResetToken that = (PasswordResetToken) o;

        return new EqualsBuilder()
                .append(token, that.token)
                .append(expiryDate, that.expiryDate)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(token)
                .append(expiryDate)
                .toHashCode();
    }
}
