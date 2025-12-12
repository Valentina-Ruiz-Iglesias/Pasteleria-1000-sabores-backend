package com.tienda.tienda_backend.controller;

import com.tienda.tienda_backend.dto.CreateOrderRequest;
import com.tienda.tienda_backend.dto.CreateOrderItemRequest;
import com.tienda.tienda_backend.dto.UpdateOrderStatusRequest;
import com.tienda.tienda_backend.entity.Order;
import com.tienda.tienda_backend.entity.OrderItem;
import com.tienda.tienda_backend.entity.Product;
import com.tienda.tienda_backend.entity.User;
import com.tienda.tienda_backend.repository.ProductRepository;
import com.tienda.tienda_backend.security.jwt.JwtUtil;
import com.tienda.tienda_backend.service.OrderService;
import com.tienda.tienda_backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final ProductRepository productRepository;
    private final JwtUtil jwtUtil;

    public OrderController(OrderService orderService,
                           UserService userService,
                           ProductRepository productRepository,
                           JwtUtil jwtUtil) {
        this.orderService = orderService;
        this.userService = userService;
        this.productRepository = productRepository;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public ResponseEntity<?> createOrder(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @RequestBody CreateOrderRequest request
    ) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        String email;
        try {
            email = jwtUtil.extractEmail(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }

        Optional<User> optionalUser = userService.getUserByEmail(email);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        }
        User user = optionalUser.get();

        Order order = new Order();
        order.setTotalAmount(request.getTotalAmount());
        order.setDiscountAmount(request.getDiscountAmount());
        order.setFinalAmount(request.getFinalAmount());
        order.setStatus("PENDING");

        List<OrderItem> items = new ArrayList<>();

        if (request.getItems() != null) {
            for (CreateOrderItemRequest itemRequest : request.getItems()) {
                Optional<Product> optionalProduct = productRepository.findById(itemRequest.getProductId());
                if (optionalProduct.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("Product not found: " + itemRequest.getProductId());
                }
                Product product = optionalProduct.get();

                OrderItem item = new OrderItem();
                item.setProduct(product);
                item.setQuantity(itemRequest.getQuantity());
                item.setUnitPrice(itemRequest.getUnitPrice());
                item.setTotalPrice(itemRequest.getTotalPrice());
                item.setOrder(order);

                items.add(item);
            }
        }

        order.setItems(items);

        Order saved = orderService.createOrderForUser(user, order);

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/my")
    public ResponseEntity<?> getMyOrders(
            @RequestHeader(value = "Authorization", required = false) String authHeader
    ) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        String email;
        try {
            email = jwtUtil.extractEmail(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }

        Optional<User> optionalUser = userService.getUserByEmail(email);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        }

        User user = optionalUser.get();
        return ResponseEntity.ok(orderService.getOrdersForUser(user));
    }

    @GetMapping
    public ResponseEntity<?> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateOrderStatus(
            @PathVariable Long id,
            @RequestBody UpdateOrderStatusRequest request
    ) {
        try {
            Order updated = orderService.updateOrderStatus(id, request.getStatus());
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
