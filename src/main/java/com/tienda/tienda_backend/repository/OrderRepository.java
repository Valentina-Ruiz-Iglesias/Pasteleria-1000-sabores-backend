package com.tienda.tienda_backend.repository;

import com.tienda.tienda_backend.entity.Order;
import com.tienda.tienda_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // para GET /orders/my
    List<Order> findByUser(User user);
}
