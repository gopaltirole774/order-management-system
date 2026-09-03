package com.org.ordermanagementsystem.service;

import com.org.ordermanagementsystem.dto.OrderPatchRequestDto;
import com.org.ordermanagementsystem.dto.OrderRequestDto;
import com.org.ordermanagementsystem.dto.OrderResponseDto;
import com.org.ordermanagementsystem.entity.Order;
import com.org.ordermanagementsystem.enums.OrderStatus;
import com.org.ordermanagementsystem.exception.OrderNotFoundException;
import com.org.ordermanagementsystem.repository.OrderRepository;
import com.org.ordermanagementsystem.service.serviceimpl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderServiceimpl;

    private Order order;
    private OrderRequestDto orderRequestDto;
    private OrderPatchRequestDto orderPatchRequestDto;

    @BeforeEach
    void setup() {
        orderRequestDto = new OrderRequestDto();
        orderRequestDto.setQuantity(23);
        orderRequestDto.setCustomerName("Arjun");
        orderRequestDto.setCustomerAddress("Khandwa");
        orderRequestDto.setProductPrice(100.0);
        orderRequestDto.setProductName("Laptop");
        orderRequestDto.setStatus(OrderStatus.PENDING);

        order = new Order();
        order.setQuantity(orderRequestDto.getQuantity());
        order.setCustomerName(orderRequestDto.getCustomerName());
        order.setCustomerAddress(orderRequestDto.getCustomerAddress());
        order.setProductPrice(orderRequestDto.getProductPrice());
        order.setProductName(orderRequestDto.getProductName());
        order.setStatus(orderRequestDto.getStatus());

        orderPatchRequestDto = new OrderPatchRequestDto();
        orderPatchRequestDto.setQuantity(25);
        orderPatchRequestDto.setProductPrice(5000.00);
        orderPatchRequestDto.setStatus(OrderStatus.DELIVERED);
        orderPatchRequestDto.setCustomerAddress(orderRequestDto.getCustomerAddress());

    }

    @Test
    void saveOrderTest() {
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        OrderResponseDto responseDto = orderServiceimpl.saveOrder(orderRequestDto);
        assertNotNull(responseDto);
        assertEquals(order.getCustomerName(), responseDto.getCustomerName());
        assertEquals(order.getCustomerAddress(), responseDto.getCustomerAddress());
        assertEquals(order.getProductName(), responseDto.getProductName());
        assertEquals(order.getProductPrice(), responseDto.getProductPrice());
        assertEquals(order.getQuantity(), responseDto.getQuantity());
        verify(orderRepository).save(any(Order.class));

    }

    @Test
    void updateOrderByIdTest() {
        when(orderRepository.findById(1)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        OrderResponseDto responseDto = orderServiceimpl.updateOrderById(1, orderRequestDto);
        assertNotNull(responseDto);
        assertEquals(order.getCustomerName(), responseDto.getCustomerName());
        assertEquals(order.getCustomerAddress(), responseDto.getCustomerAddress());
        verify(orderRepository).findById(1);
        verify(orderRepository).save(any(Order.class));
    }

    @Test
    void updateOrderById_NotFoundTest() {
        when(orderRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(OrderNotFoundException.class, () -> orderServiceimpl.updateOrderById(1, orderRequestDto));
        verify(orderRepository).findById(1);
    }

    @Test
    void getOrderByIDTest() {
        when(orderRepository.findById(1)).thenReturn(Optional.of(order));
        OrderResponseDto responseDto = orderServiceimpl.getOrderByID(1);
        assertNotNull(responseDto);
        assertEquals(order.getCustomerName(), responseDto.getCustomerName());
        assertEquals(order.getCustomerAddress(), responseDto.getCustomerAddress());
        assertEquals(order.getProductName(), responseDto.getProductName());
        assertEquals(order.getProductPrice(), responseDto.getProductPrice());
        assertEquals(order.getQuantity(), responseDto.getQuantity());
        verify(orderRepository).findById(1);
    }

    @Test
    void getOrderByID_NotFoundTest() {
        when(orderRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(OrderNotFoundException.class, () -> orderServiceimpl.getOrderByID(1));
        verify(orderRepository).findById(1);
    }

    @Test
    void patchOrderByIdTest() {
        when(orderRepository.findById(1)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        OrderResponseDto responseDto = orderServiceimpl.patchOrderById(1, orderPatchRequestDto);
        assertNotNull(responseDto);
        assertEquals(order.getCustomerName(), responseDto.getCustomerName());
        assertEquals(order.getCustomerAddress(), responseDto.getCustomerAddress());
        assertEquals(order.getProductName(), responseDto.getProductName());
        assertEquals(order.getProductPrice(), responseDto.getProductPrice());
        assertEquals(order.getQuantity(), responseDto.getQuantity());
        verify(orderRepository).findById(1);
        verify(orderRepository).save(any(Order.class));
    }

    @Test
    void patchOrderById_NotFoundTest() {
        when(orderRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(OrderNotFoundException.class, () -> orderServiceimpl.patchOrderById(1, orderPatchRequestDto));
        verify(orderRepository).findById(1);
    }

    @Test
    void deleteOrderByIdTest() {
        when(orderRepository.findById(1)).thenReturn(Optional.of(order));
        orderServiceimpl.deleteOrderById(1);
        verify(orderRepository).deleteById(1);
        verify(orderRepository).findById(1);
    }

    @Test
    void deleteOrderById_NotFoundTest() {
        when(orderRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(OrderNotFoundException.class, () -> orderServiceimpl.deleteOrderById(1));
        verify(orderRepository).findById(1);
    }

    @Test
    void getAllOrdersTest() {
        when(orderRepository.findAll()).thenReturn(List.of(order));
        List<OrderResponseDto> responseDtos = orderServiceimpl.getAllOrders();
        assertNotNull(responseDtos);
        assertEquals(1,responseDtos.size());
        verify(orderRepository).findAll();
    }

    @Test
    void getAllOrdersEmptyTest() {
        when(orderRepository.findAll()).thenReturn(List.of());
        List<OrderResponseDto> responseDtos = orderServiceimpl.getAllOrders();
        assertNotNull(responseDtos);
        assertEquals(0, responseDtos.size());
        verify(orderRepository).findAll();
    }

}
