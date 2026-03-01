package com.order_management.Order_management_system.model;
import com.order_management.Order_management_system.enums.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class Order {
    private UUID order_id;
    private UUID memberId;
    private Integer product_id;
    private String product_name;
    private Integer product_quantity;
    private double total_price;
    private OrderType order_type;
    private Status status;
    private LocalDateTime timestamp;

}
