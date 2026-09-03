package com.org.ordermanagementsystem.controller;

import com.org.ordermanagementsystem.dto.OrderPatchRequestDto;
import com.org.ordermanagementsystem.dto.OrderRequestDto;
import com.org.ordermanagementsystem.dto.OrderResponseDto;
import com.org.ordermanagementsystem.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@Tag(name = "Order Management",description = "Endpoints for managing orders")
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @Operation(summary = "Create new order",description = "Creating a new order with the provided details")
    @ApiResponses({ @ApiResponse(responseCode = "201", description = "Order created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid order data provided")
    })

    public ResponseEntity<OrderResponseDto> saveOrder(@Valid @RequestBody OrderRequestDto requestDto) {
        return new ResponseEntity<>(orderService.saveOrder(requestDto), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all orders", description = "Retrieving a list of all existing orders")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Orders retrieved successfully") })
    public ResponseEntity<List<OrderResponseDto>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get order by ID", description = "Retrieving an existing order by its ID")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Order retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Order not found") })
    public ResponseEntity<OrderResponseDto> getOrderByID(@PathVariable Integer id) {
        return ResponseEntity.ok(orderService.getOrderByID(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update order by ID", description = "Updating an existing order by its ID with the provided details")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Order updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid order data provided"),
            @ApiResponse(responseCode = "404", description = "Order not found") })
    public ResponseEntity<OrderResponseDto> updateOrderById(@PathVariable Integer id, @Valid @RequestBody OrderRequestDto requestDto) {
        return ResponseEntity.ok(orderService.updateOrderById(id, requestDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete order by ID", description = "Deleting an existing order by its ID")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Order deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Order not found") })
    public ResponseEntity<String> deleteOrderById(@PathVariable Integer id) {
        orderService.deleteOrderById(id);
        return ResponseEntity.ok("Order deleted successfully");
    }

    @PatchMapping("/{id}")
    @Operation(summary = " Patch (Update) order by ID", description = "Partially updating an existing order by its ID with the provided details")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Order updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid order data provided"),
            @ApiResponse(responseCode = "404", description = "Order not found") })
    public ResponseEntity<OrderResponseDto> patchOrderById(@PathVariable Integer id, @RequestBody OrderPatchRequestDto patchRequestDto) {
        return ResponseEntity.ok(orderService.patchOrderById(id, patchRequestDto));
    }

}
