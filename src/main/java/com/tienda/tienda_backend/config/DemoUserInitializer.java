package com.tienda.tienda_backend.config;

import com.tienda.tienda_backend.entity.User;
import com.tienda.tienda_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DemoUserInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        userRepository.findByEmail("admin@gmail.com")
                .ifPresentOrElse(
                        user -> {},
                        () -> {
                            User admin = new User();
                            admin.setEmail("admin@gmail.com");
                            admin.setName("Administrador");
                            admin.setRole("ADMIN");
                            admin.setPassword(passwordEncoder.encode("admin123"));
                            admin.setDateOfBirth(LocalDate.of(2000, 1, 1));
                            admin.setHasFelices50(false);
                            admin.setIsDuocStudent(false);
                            userRepository.save(admin);
                        }
                );

        userRepository.findByEmail("paul.caro@duoc.cl")
                .ifPresentOrElse(
                        user -> {},
                        () -> {
                            User paula = new User();
                            paula.setEmail("paul.caro@duoc.cl");
                            paula.setName("Paula Caro");
                            paula.setRole("CLIENT");
                            paula.setPassword(passwordEncoder.encode("user123"));
                            paula.setDateOfBirth(LocalDate.of(2001, 4, 6));
                            paula.setHasFelices50(false);
                            paula.setIsDuocStudent(true);
                            userRepository.save(paula);
                        }
                );
    }
}
