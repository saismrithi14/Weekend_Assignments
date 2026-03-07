package com.order_management.Order_management_system.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Integer product_id;
    private String product_name;
    private Integer availableQuantity;
    private double product_price;
}
