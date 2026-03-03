package com.alderson.demo.model;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserModel implements Model {
    // looks like class for service layer, where is dto for controller?
    public Timestamp updatedAt;

    private String name;
    private String email;
    private LocalDate dateOfBirth;
    private Timestamp createdAt;

    public UserModel(String name, String email, String dateOfBirth) {
        this.name = name;
        this.email = email;
        this.dateOfBirth = LocalDate.parse(dateOfBirth);
        this.createdAt = Timestamp.valueOf(LocalDateTime.now());
    }

    public UserModel(String name, String email, LocalDate dateOfBirth, Timestamp createdAt, Timestamp updatedAt) {
        this.updatedAt = updatedAt;
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

}