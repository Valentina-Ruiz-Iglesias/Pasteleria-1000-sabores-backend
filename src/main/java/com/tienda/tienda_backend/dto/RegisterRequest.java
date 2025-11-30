package com.tienda.tienda_backend.dto;

import java.time.LocalDate;

public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private LocalDate dateOfBirth;
    private String code;
    private Boolean isDuocStudent;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getIsDuocStudent() {
        return isDuocStudent;
    }

    public void setIsDuocStudent(Boolean isDuocStudent) {
        this.isDuocStudent = isDuocStudent;
    }
}
