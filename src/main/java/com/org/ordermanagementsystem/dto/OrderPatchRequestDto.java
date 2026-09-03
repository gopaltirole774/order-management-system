package com.org.ordermanagementsystem.dto;

import com.org.ordermanagementsystem.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderPatchRequestDto {

    private String customerName;
    private  String customerAddress;
    private String productName;
    private Double productPrice;
    private Integer quantity;
    private OrderStatus status;
}
