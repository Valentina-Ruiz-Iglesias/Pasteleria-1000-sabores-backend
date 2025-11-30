package com.tienda.tienda_backend.service;

import com.tienda.tienda_backend.entity.Order;
import com.tienda.tienda_backend.entity.User;

import java.util.List;

public interface OrderService {

    Order createOrderForUser(User user, Order order);

    List<Order> getOrdersForUser(User user);

    List<Order> getAllOrders();
}
