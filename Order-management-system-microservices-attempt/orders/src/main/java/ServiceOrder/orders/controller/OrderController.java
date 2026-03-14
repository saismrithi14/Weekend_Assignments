package ServiceOrder.orders.controller;

import ServiceOrder.orders.dto.OrderDTO;
import ServiceOrder.orders.dto.ProductDTO;
import ServiceOrder.orders.dto.ProductQuantityDTO;
import ServiceOrder.orders.model.Order;
import ServiceOrder.orders.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/oms/orders")
public class OrderController {
    @Autowired
    OrderService orderService;

    @GetMapping("exists/member/{memberId}")
    public boolean isMemberPresent(@PathVariable UUID memberId)
    {
        return orderService.isMemberPresent(memberId);
    }

    @GetMapping("exists/product/{product_name}")
    public ProductDTO getProductInfoByNameIfExists(@PathVariable String product_name)
    {
        return orderService.getProductInfoByNameIfExists(product_name);
    }

    @PutMapping("/update")
    public double updateProductTable(@RequestBody ProductQuantityDTO dto)
    {
        return orderService.updateProductTable(dto);
    }

    @PostMapping("/create")
    public void addOrder(@RequestBody @Valid OrderDTO dto)
    {
        orderService.addOrder(dto);
    }

    @GetMapping("/all")
    public List<Order> getAllOrders()
    {
        return orderService.getAllOrders();
    }

    @DeleteMapping("/{order_id}")
    public void deleteOrder(@PathVariable UUID order_id)
    {
        orderService.deleteOrder(order_id);
    }

}

