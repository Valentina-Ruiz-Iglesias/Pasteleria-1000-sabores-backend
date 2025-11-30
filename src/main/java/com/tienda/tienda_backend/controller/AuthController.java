package com.tienda.tienda_backend.controller;

import com.tienda.tienda_backend.dto.RegisterRequest;
import com.tienda.tienda_backend.entity.User;
import com.tienda.tienda_backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword()); // se encripta en el service
        user.setDateOfBirth(request.getDateOfBirth());
        user.setIsDuocStudent(Boolean.TRUE.equals(request.getIsDuocStudent()));
        user.setHasFelices50(false); // por defecto
        user.setRole("CLIENT"); // por defecto

        User saved = userService.createUser(user);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }
}
