package com.tienda.tienda_backend.testservice;

import com.tienda.tienda_backend.entity.Order;
import com.tienda.tienda_backend.entity.OrderItem;
import com.tienda.tienda_backend.entity.User;
import com.tienda.tienda_backend.repository.OrderItemRepository;
import com.tienda.tienda_backend.repository.OrderRepository;
import com.tienda.tienda_backend.service.OrderServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void createOrderForUser_setsUserDateStatusAndSaves() {
        User user = new User();
        user.setId(1L);

        OrderItem item1 = new OrderItem();
        OrderItem item2 = new OrderItem();

        Order order = new Order();
        order.setItems(List.of(item1, item2));
        order.setStatus(null);

        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Order result = orderService.createOrderForUser(user, order);

        assertThat(result.getUser()).isEqualTo(user);
        assertThat(result.getCreatedAt()).isNotNull();
        assertThat(result.getStatus()).isEqualTo("PENDING");

        assertThat(item1.getOrder()).isEqualTo(result);
        assertThat(item2.getOrder()).isEqualTo(result);

        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
        verify(orderRepository, times(1)).save(orderCaptor.capture());
        Order saved = orderCaptor.getValue();
        assertThat(saved.getUser()).isEqualTo(user);
    }

    @Test
    void getOrdersForUser_returnsOrdersFromRepository() {
        User user = new User();
        user.setId(1L);

        Order order1 = new Order();
        Order order2 = new Order();
        when(orderRepository.findByUser(user)).thenReturn(List.of(order1, order2));

        List<Order> result = orderService.getOrdersForUser(user);

        assertThat(result).hasSize(2).containsExactly(order1, order2);
        verify(orderRepository, times(1)).findByUser(user);
    }

    @Test
    void getAllOrders_returnsAllFromRepository() {
        Order order1 = new Order();
        Order order2 = new Order();
        when(orderRepository.findAll()).thenReturn(List.of(order1, order2));

        List<Order> result = orderService.getAllOrders();

        assertThat(result).hasSize(2).containsExactly(order1, order2);
        verify(orderRepository, times(1)).findAll();
    }
}
