package com.org.ordermanagementsystem.controller;

import com.org.ordermanagementsystem.dto.OrderPatchRequestDto;
import com.org.ordermanagementsystem.dto.OrderRequestDto;
import com.org.ordermanagementsystem.dto.OrderResponseDto;
import com.org.ordermanagementsystem.enums.OrderStatus;
import com.org.ordermanagementsystem.exception.OrderNotFoundException;
import com.org.ordermanagementsystem.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    private OrderRequestDto orderRequestDto;
    private OrderPatchRequestDto orderPatchRequestDto;
    private OrderResponseDto orderResponseDto;

    @BeforeEach
    void setUp() {
        orderRequestDto = new OrderRequestDto();

        orderRequestDto.setCustomerName("John Doe");
        orderRequestDto.setProductName("Laptop");
        orderRequestDto.setQuantity(1);
        orderRequestDto.setProductPrice(1000.0);
        orderRequestDto.setCustomerAddress("123 Main St");
        orderRequestDto.setStatus(OrderStatus.CANCELLED);

        orderPatchRequestDto = new OrderPatchRequestDto();
        orderPatchRequestDto.setQuantity(5);
        orderPatchRequestDto.setProductPrice(2300.0);

        orderResponseDto = new OrderResponseDto();
        orderResponseDto.setId(1);
        orderResponseDto.setQuantity(orderRequestDto.getQuantity());
        orderResponseDto.setStatus(orderRequestDto.getStatus());
        orderResponseDto.setCustomerName(orderRequestDto.getCustomerName());
        orderResponseDto.setProductName(orderRequestDto.getProductName());
        orderResponseDto.setProductPrice(orderRequestDto.getProductPrice());
        orderResponseDto.setCustomerAddress(orderRequestDto.getCustomerAddress());

    }

    @Test
    void saveOrderTest() throws Exception {

        when(orderService.saveOrder(any(OrderRequestDto.class))).thenReturn(orderResponseDto);
        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequestDto)))
                .andExpect(status().isCreated());
        verify(orderService).saveOrder(any(OrderRequestDto.class));

    }

    @Test
    void getAllOrdersTest() throws Exception {

        when(orderService.getAllOrders()).thenReturn(List.of(orderResponseDto));
        mockMvc.perform(get("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequestDto)))
                .andExpect(status().isOk());
    }

    @Test
    void getAllOrdersEmptyListTest() throws Exception {

        when(orderService.getAllOrders()).thenReturn(List.of());
        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(0));
        verify(orderService).getAllOrders();


    }

    @Test
    void getOrderByIDTest() throws Exception {

        when(orderService.getOrderByID(1)).thenReturn(orderResponseDto);
        mockMvc.perform(get("/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.customerName").value("John Doe"))
                .andExpect(jsonPath("$.productName").value("Laptop"))
                .andExpect(jsonPath("$.quantity").value(1))
                .andExpect(jsonPath("$.productPrice").value(1000.0))
                .andExpect(jsonPath("$.customerAddress").value("123 Main St"))
                .andExpect(jsonPath("$.status").value("CANCELLED"));
        verify(orderService).getOrderByID(1);


    }

    @Test
    void getOrderByIDNotFoundTest() throws Exception {

        when(orderService.getOrderByID(1)).thenThrow(new OrderNotFoundException("Order not found"));
        mockMvc.perform(get("/orders/1"))
                .andExpect(status().isNotFound());


    }

    @Test
    void updateOrderByIdTest() throws Exception {
        when(orderService.updateOrderById(eq(1), any(OrderRequestDto.class))).thenReturn(orderResponseDto);
        mockMvc.perform(put("/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.customerName").value("John Doe"))
                .andExpect(jsonPath("$.productName").value("Laptop"))
                .andExpect(jsonPath("$.quantity").value(1))
                .andExpect(jsonPath("$.productPrice").value(1000.0))
                .andExpect(jsonPath("$.customerAddress").value("123 Main St"))
                .andExpect(jsonPath("$.status").value("CANCELLED"));
        verify(orderService).updateOrderById(eq(1), any(OrderRequestDto.class));

    }

    @Test
    void updateOrderByIdNotFoundTest() throws Exception {
        when(orderService.updateOrderById(eq(1), any(OrderRequestDto.class)))
                .thenThrow(new OrderNotFoundException("Order not found"));
        mockMvc.perform(put("/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequestDto)))
                .andExpect(status().isNotFound());

    }

    @Test
    void deleteOrderByIdTest() throws Exception {
        mockMvc.perform(delete("/orders/1"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteOrderByIdNotFoundTest() throws Exception {
        doThrow(new OrderNotFoundException("Order not found")).when(orderService).deleteOrderById(1);
        mockMvc.perform(delete("/orders/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void patchOrderByIdTest() throws Exception {
        when(orderService.patchOrderById(eq(1), any(OrderPatchRequestDto.class))).thenReturn(orderResponseDto);
        mockMvc.perform(patch("/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderPatchRequestDto)))
                .andExpect(status().isOk());
        verify(orderService).patchOrderById(eq(1), any(OrderPatchRequestDto.class));
    }

    @Test
    void patchOrderByIdNotFoundTest() throws Exception {
        when(orderService.patchOrderById(eq(1), any(OrderPatchRequestDto.class)))
                .thenThrow(new OrderNotFoundException("Order not found"));
        mockMvc.perform(patch("/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderPatchRequestDto)))
                .andExpect(status().isNotFound());
    }


}
