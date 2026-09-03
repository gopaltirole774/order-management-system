package com.org.ordermanagementsystem.service.serviceimpl;

import com.org.ordermanagementsystem.dto.OrderPatchRequestDto;
import com.org.ordermanagementsystem.dto.OrderRequestDto;
import com.org.ordermanagementsystem.dto.OrderResponseDto;
import com.org.ordermanagementsystem.entity.Order;
import com.org.ordermanagementsystem.enums.OrderStatus;
import com.org.ordermanagementsystem.exception.OrderNotFoundException;
import com.org.ordermanagementsystem.repository.OrderRepository;
import com.org.ordermanagementsystem.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private static final String ORDER_NOT_FOUND = "Order not found";

    OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    private Order toEntity(OrderRequestDto requestDto) {

        Order order = new Order();

        order.setCustomerName(requestDto.getCustomerName());
        order.setCustomerAddress(requestDto.getCustomerAddress());
        order.setStatus(requestDto.getStatus());
        order.setProductPrice(requestDto.getProductPrice());
        order.setProductName(requestDto.getProductName());
        order.setQuantity(requestDto.getQuantity());
        order.setStatus(OrderStatus.PENDING);
        return order;
    }

    private OrderResponseDto toResponse(Order order) {
        OrderResponseDto responseDto = new OrderResponseDto();

        responseDto.setId(order.getId());
        responseDto.setCustomerName(order.getCustomerName());
        responseDto.setCustomerAddress(order.getCustomerAddress());
        responseDto.setQuantity(order.getQuantity());
        responseDto.setProductPrice(order.getProductPrice());
        responseDto.setProductName(order.getProductName());
        responseDto.setStatus(order.getStatus());
        return responseDto;


    }

    @Override
    public OrderResponseDto saveOrder(OrderRequestDto requestDto) {
        Order order = toEntity(requestDto);
        Order saveOrder = orderRepository.save(order);
        return toResponse(saveOrder);
    }

    @Override
    public OrderResponseDto getOrderByID(Integer id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(ORDER_NOT_FOUND));
        return toResponse(order);
    }

    @Override
    public OrderResponseDto updateOrderById(Integer id, OrderRequestDto requestDto) {
        Order existingOrder = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(ORDER_NOT_FOUND));

        existingOrder.setCustomerName(requestDto.getCustomerName());
        existingOrder.setCustomerAddress(requestDto.getCustomerAddress());
        existingOrder.setQuantity(requestDto.getQuantity());
        existingOrder.setProductPrice(requestDto.getProductPrice());
        existingOrder.setProductName(requestDto.getProductName());
        existingOrder.setStatus(requestDto.getStatus());

        Order updatedOrder = orderRepository.save(existingOrder);
        return toResponse(updatedOrder);
    }

    @Override
    public void deleteOrderById(Integer id) {
        orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(ORDER_NOT_FOUND));
        orderRepository.deleteById(id);
    }

    @Override
    public List<OrderResponseDto> getAllOrders() {

        List<Order> orders = orderRepository.findAll();
        List<OrderResponseDto> responseDtoList = new ArrayList<>();
        for (Order order : orders) {
            responseDtoList.add(toResponse(order));
        }

        return responseDtoList;

    }

    @Override
    public OrderResponseDto patchOrderById(Integer id, OrderPatchRequestDto patchRequestDto) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(ORDER_NOT_FOUND));
        if (patchRequestDto.getCustomerName() != null) {
            order.setCustomerName(patchRequestDto.getCustomerName());
        }
        if (patchRequestDto.getQuantity() != null) {
            order.setQuantity(patchRequestDto.getQuantity());
        }
        if (patchRequestDto.getStatus() != null) {
            order.setStatus(patchRequestDto.getStatus());
        }
        if (patchRequestDto.getCustomerAddress() != null) {
            order.setCustomerAddress(patchRequestDto.getCustomerAddress());
        }
        if (patchRequestDto.getProductPrice() != null) {
            order.setProductPrice(patchRequestDto.getProductPrice());
        }
        if (patchRequestDto.getProductName() != null) {
            order.setProductName(patchRequestDto.getProductName());
        }
        return toResponse(orderRepository.save(order));
    }


}
