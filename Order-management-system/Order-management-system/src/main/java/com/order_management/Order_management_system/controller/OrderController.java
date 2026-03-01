package com.order_management.Order_management_system.controller;

import java.util.*;
import com.order_management.Order_management_system.dto.OrderDTO;
import com.order_management.Order_management_system.model.Order;
import com.order_management.Order_management_system.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/oms/orders")
public class OrderController {

    @Autowired
    OrderService orderService;
    @PostMapping("/create")
    public ResponseEntity<String> addOrder(@RequestBody @Valid OrderDTO dto)
    {
        orderService.addOrder(dto);
        return ResponseEntity.ok("Order created successfully");
    }

    @DeleteMapping("/{order_id}")
    public ResponseEntity<String> deleteOrder(@PathVariable UUID order_id)
    {
        orderService.deleteOrder(order_id);
        return ResponseEntity.ok("Order deleted successfully");
    }

    @GetMapping("/all")
    public List<Order> getAllOrders()
    {
        return orderService.getAllOrders();
    }

}
