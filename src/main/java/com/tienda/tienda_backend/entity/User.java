package com.tienda.tienda_backend.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 30)
    private String role; // ADMIN, SELLER, CLIENT

    private LocalDate dateOfBirth;

    @Column(nullable = false)
    private Boolean isDuocStudent = false;

    @Column(nullable = false)
    private Boolean hasFelices50 = false;

    @Column(nullable = false, unique = true, length = 9)
    private String run;

    @Column(nullable = false, length = 100)
    private String apellidos;


    public User() {
    }

    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Boolean getIsDuocStudent() {
        return isDuocStudent;
    }

    public void setIsDuocStudent(Boolean isDuocStudent) {
        this.isDuocStudent = isDuocStudent;
    }

    public Boolean getHasFelices50() {
        return hasFelices50;
    }

    public void setHasFelices50(Boolean hasFelices50) {
        this.hasFelices50 = hasFelices50;
    }
}
