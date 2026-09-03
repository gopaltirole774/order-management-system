package com.org.ordermanagementsystem.dto;

import com.org.ordermanagementsystem.enums.OrderStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {

    @NotBlank(message = "Customer Name should not be blank")
    private String customerName;

    @NotBlank(message = "Customer Address should not be blank")
    private  String customerAddress;

    @NotBlank(message = "Product Name should not be blank")
    private String productName;

    @NotNull(message = "Product Price should not be null")
    @Min(value = 1, message = "Product Price should not be less than 1")
    @Positive(message = "Product Price should not be negative")
    private Double productPrice;

    @NotNull(message = "Quantity should not be null")
    private Integer quantity;

    @NotNull(message = "Status should not be null")
    private OrderStatus status;

}
