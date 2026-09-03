package com.org.ordermanagementsystem.entity;

import com.org.ordermanagementsystem.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String customerName;
    private  String customerAddress;
    private String productName;
    private Double productPrice;
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;


}
