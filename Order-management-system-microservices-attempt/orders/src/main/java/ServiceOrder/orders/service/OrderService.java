package ServiceOrder.orders.service;

import ServiceOrder.orders.client.MemberClient;
import ServiceOrder.orders.client.ProductClient;
import ServiceOrder.orders.dto.OrderDTO;
import ServiceOrder.orders.dto.ProductDTO;
import ServiceOrder.orders.dto.ProductQuantityDTO;
import ServiceOrder.orders.exceptions.MemberNotFoundException;
import ServiceOrder.orders.exceptions.OrderNotFoundException;
import ServiceOrder.orders.exceptions.ProductNotFoundException;
import ServiceOrder.orders.exceptions.QuantityLimitExceededException;
import ServiceOrder.orders.model.Order;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class OrderService {
    ConcurrentHashMap<UUID, Order> order_table = new ConcurrentHashMap<>();
    private final Object memberLock = new Object();
    private final Object logLock = new Object();
    private final Object orderLock = new Object();
    @Autowired
    MemberClient memberClient;

    @Autowired
    ProductClient productClient;


    public void addOrder(OrderDTO dto)
    {
        Order new_order = new Order();
        UUID memberId = dto.getMemberId();
        if(!isMemberPresent(memberId))
        {
            throw new MemberNotFoundException("Member with id " + memberId + " is not present");
        }

        new_order.setMemberId(dto.getMemberId());
        //extracting the product name
        String product_name = dto.getProduct_name();
        ProductDTO result = getProductInfoByNameIfExists(product_name);
        if(result == null)
        {
            throw new ProductNotFoundException("Product with name: " + product_name + " doesn't exist");
        }

        //If the product exists we get the product_id and name from the client
        ProductDTO p = result;
        new_order.setProduct_id(p.getProduct_id());
        new_order.setProduct_name(p.getProduct_name());


        //updating the product table
        ProductQuantityDTO quantDTO = new ProductQuantityDTO();
        quantDTO.setProduct_id(new_order.getProduct_id());
        quantDTO.setOrder_type(dto.getOrder_type());
        quantDTO.setProduct_quantity(dto.getProduct_quantity());

        try {
            double price = updateProductTable(quantDTO);
            new_order.setTotal_price(price);
        }
        catch(FeignException fe)
        {
            throw new QuantityLimitExceededException("Quantity desired exceeds the quantity available");
        }

        new_order.setProduct_quantity(dto.getProduct_quantity());
        new_order.setOrder_type(dto.getOrder_type());
        new_order.setStatus(dto.getStatus());
        new_order.setTimestamp(LocalDateTime.now());
        new_order.setOrder_id(UUID.randomUUID());

        order_table.put(new_order.getOrder_id(), new_order);

        synchronized(logLock)
        {
            writeToLog(new_order);
        }
    }

    public ProductDTO getProductInfoByNameIfExists(String product_name)
    {
        return productClient.getProductInfoByNameIfExists(product_name);
    }

    public boolean isMemberPresent(UUID memberId)
    {
        return memberClient.containsMember(memberId);
    }

    public double updateProductTable(ProductQuantityDTO dto)
    {
        try {
            return productClient.updateProductTable(dto);
        }

        catch(FeignException fe)
        {
            throw new QuantityLimitExceededException("Quantity desired exceeds the quantity available");
        }
    }

    public List<Order> getAllOrders()
    {
        List<Order> orders_list = new ArrayList<>();
        order_table.forEach((key, value) -> orders_list.add(value));
        return orders_list;
    }

    private void writeToLog(Order order)
    {
        Path log_path = Paths.get("C:/Users/SaiSmrithiMuthukumar/git_repo/Weekend_Assignments/Order-management-system-microservices-attempt/orders/logs/orders_list.log");
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
        if(!order_table.containsKey(order_id))
        {
            throw new OrderNotFoundException("Order with id " + order_id + " doesn't exist");
        }

        Order order = order_table.get(order_id);
        ProductQuantityDTO dto = new ProductQuantityDTO();
        dto.setProduct_id(order.getProduct_id());
        dto.setProduct_quantity(order.getProduct_quantity());
        dto.setOrder_type(order.getOrder_type());

        updateProductTableDelete(dto);

        order_table.remove(order_id);

    }

    public void updateProductTableDelete(ProductQuantityDTO dto)
    {
        productClient.updateProductTableDelete(dto);
    }

}
