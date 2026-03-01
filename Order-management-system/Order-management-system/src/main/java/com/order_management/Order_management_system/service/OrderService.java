package com.order_management.Order_management_system.service;

import com.order_management.Order_management_system.dto.OrderDTO;
import com.order_management.Order_management_system.exception.*;
import com.order_management.Order_management_system.model.Order;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.order_management.Order_management_system.enums.OrderType;
import java.nio.file.*;
import java.io.*;
import java.util.*;

import java.time.LocalDateTime;
import java.util.UUID;

import java.util.concurrent.ConcurrentHashMap;

@Service
@Data
@NoArgsConstructor
@AllArgsConstructor

public class OrderService {
    ConcurrentHashMap<UUID, Order> order_table = new ConcurrentHashMap<>();
    private final Object memberLock = new Object();
    private final Object logLock = new Object();
    private final Object orderLock = new Object();
    @Autowired
    MemberService memberService;

    @Autowired
    ProductService productService;

    public void addOrder(OrderDTO dto)
    {

        Order order = new Order();
        UUID memberId = dto.getMemberId();
        if(!memberService.containsMember(memberId))
        {
            throw new MemberNotFoundException("Member with id " + memberId + " doesn't exist");
        }

        order.setMemberId(memberId);
        String product_name = dto.getProduct_name();
        if(!productService.containsProduct(product_name))
        {
            throw new ProductNotFoundException("Product with the name: " + product_name + " doesn't exist");
        }

        order.setProduct_name(dto.getProduct_name());
        order.setProduct_id(productService.returnId(product_name));

        synchronized(memberLock) {
            //Checking if the quantity the user is ordering is within the range of the quantity available
            Integer availableQuantity = productService.getProductTable().get(productService.returnId(product_name)).getAvailableQuantity();
            if (dto.getOrder_type() == OrderType.BUY && dto.getProduct_quantity() > availableQuantity) {
                throw new QuantityLimitExceededException("The quantity requested exceeds the available quantity");
            }

            order.setProduct_quantity(dto.getProduct_quantity());
            order.setStatus(dto.getStatus());
            order.setOrder_type(dto.getOrder_type());

            if (dto.getOrder_type() == OrderType.BUY) {
                productService.getProductTable().get(productService.returnId(product_name)).setAvailableQuantity(availableQuantity - dto.getProduct_quantity());
            } else {
                productService.getProductTable().get(productService.returnId(product_name)).setAvailableQuantity(availableQuantity + dto.getProduct_quantity());
            }

        }
        order.setOrder_id(UUID.randomUUID());

        double price = productService.getProductTable().get(productService.returnId(product_name)).getProduct_price();
        order.setTotal_price(order.getProduct_quantity() * price);

        order.setTimestamp(LocalDateTime.now());
        order_table.put(order.getOrder_id(), order);
        synchronized(logLock) {
            writeToLog(order);
        }

    }

    private void writeToLog(Order order)
    {
        Path log_path = Paths.get("C:/Users/SaiSmrithiMuthukumar/git_repo/Weekend_Assignments/Order-management-system/Order-management-system/src/main/java/com/order_management/Order_management_system/logs/order.log");
        StringBuilder sb = new StringBuilder();
        if(Files.notExists(log_path))
        {
            sb.append("ORDER LOG\n");
            sb.append("Order id").append("|").append("Member id").append("|").append("Product id").append("|")
                    .append("Product Name").append("|").append("Product quantity").append("|")
                    .append("Total Price").append("|").append("Order type").append("|")
                    .append("Order status").append("|").append("Timestamp").append("\n");

            try
            {
                Files.writeString(log_path, sb.toString());
            }

            catch(IOException e)
            {
                System.out.println("An exception occurred while trying to write to the file");
                System.out.println("Working Directory: " + System.getProperty("user.dir"));
                e.printStackTrace();
            }

            finally
            {
                sb.setLength(0);
            }
        }

        sb.append(order.getOrder_id()).append("|").append(order.getMemberId()).append("|")
                .append(order.getProduct_id()).append("|").append(order.getProduct_name())
                .append("|").append(order.getProduct_quantity()).append("|").append(order.getTotal_price())
                .append("|").append(order.getOrder_type()).append("|").append(order.getStatus())
                .append("|").append(order.getTimestamp()).append("\n");

        try
        {
            Files.writeString(log_path, sb.toString(), StandardOpenOption.APPEND);
        }

        catch(IOException e)
        {
            System.out.println("An error occurred while trying to write to the file");
            System.out.println("Working Directory: " + System.getProperty("user.dir"));
            e.printStackTrace();
        }
    }

    public void deleteOrder(UUID order_id)
    {
        Order order = order_table.get(order_id);
        if(order == null)
        {
            throw new OrderNotFoundException("The order with the order id " + order_id + " doesn't exist");
        }

        /*
        int product_id = order.getProduct_id();
        Integer quantity = order.getProduct_quantity();
        Integer available_quantity = productService.getProductTable().get(product_id).getAvailableQuantity();
        */
        synchronized(orderLock) {
            int product_id = order.getProduct_id();
            Integer quantity = order.getProduct_quantity();
            Integer available_quantity = productService.getProductTable().get(product_id).getAvailableQuantity();

            if (order.getOrder_type() == OrderType.BUY) {
                productService.getProductTable().get(product_id).setAvailableQuantity(quantity + available_quantity);
            } else {
                if (quantity > available_quantity) {
                    throw new QuantityLimitExceededException("The quantity exceeds the available quantity");
                }
                productService.getProductTable().get(product_id).setAvailableQuantity(available_quantity - quantity);
            }

        }
        order_table.remove(order_id);
    }

    public List<Order> getAllOrders()
    {
        List<Order> orders_list = new ArrayList<>();
        order_table.forEach((key, value) -> orders_list.add(value));
        return orders_list;
    }

}
