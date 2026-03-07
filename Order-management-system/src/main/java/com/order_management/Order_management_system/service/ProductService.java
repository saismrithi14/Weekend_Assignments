package com.order_management.Order_management_system.service;

import com.order_management.Order_management_system.model.Product;
import jakarta.annotation.PostConstruct;
import lombok.*;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductService {
    private ConcurrentHashMap<Integer, Product> productTable = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Integer> nameToIdMapping = new ConcurrentHashMap<>();

    @PostConstruct
    public void initProducts()
    {
        addProduct(new Product(1, "iPhone 15", 50, 1200.00));
        addProduct(new Product(2, "Samsung Galaxy S24", 40, 1100.00));
        addProduct(new Product(3, "MacBook Pro", 25, 2500.00));
        addProduct(new Product(4, "Dell XPS 15", 30, 2200.00));
        addProduct(new Product(5, "Sony Headphones", 100, 300.00));
        addProduct(new Product(6, "iPad Air", 60, 800.00));
        addProduct(new Product(7, "PlayStation 5", 35, 600.00));
        addProduct(new Product(8, "Logitech Mouse", 200, 50.00));
        addProduct(new Product(9, "Mechanical Keyboard", 120, 150.00));
        addProduct(new Product(10, "4K Monitor", 45, 450.00));
    }

    private void addProduct(Product P)
    {
        productTable.put(P.getProduct_id(), P);
        nameToIdMapping.put(P.getProduct_name(),P.getProduct_id());
    }

    public boolean containsProduct(String product_name)
    {
        return nameToIdMapping.containsKey(product_name);
    }

    public Integer returnId(String product_name)
    {
        return nameToIdMapping.get(product_name);
    }

}
