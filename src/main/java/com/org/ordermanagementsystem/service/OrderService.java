package com.org.ordermanagementsystem.service;

import com.org.ordermanagementsystem.dto.OrderPatchRequestDto;
import com.org.ordermanagementsystem.dto.OrderRequestDto;
import com.org.ordermanagementsystem.dto.OrderResponseDto;

import java.util.List;

public interface OrderService {
    OrderResponseDto saveOrder(OrderRequestDto requestDto);
    List<OrderResponseDto>getAllOrders();
    OrderResponseDto getOrderByID(Integer id);
    OrderResponseDto updateOrderById(Integer id, OrderRequestDto requestDto);
    void deleteOrderById(Integer id);
    OrderResponseDto patchOrderById(Integer id, OrderPatchRequestDto patchRequestDto);



}
