package ServiceReport.reports.service;

import ServiceReport.reports.client.orderClient;
import ServiceReport.reports.enums.OrderType;
import ServiceReport.reports.enums.Status;
import ServiceReport.reports.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportService {

    @Autowired
    private orderClient oc;

    public List<Order> getAllOrders()
    {
        return oc.getAllOrders();
    }

    public ResponseEntity<String> getTotalAmount()
    {
        List<Order> orderList = oc.getAllOrders();
        double total_amount = orderList.stream().mapToDouble(Order::getTotal_price).sum();
        return ResponseEntity.ok("Total amount ordered is: " + total_amount);
    }

    public ResponseEntity<Map<OrderType, Long>> totalBuyVsSell()
    {
        List<Order> orders = oc.getAllOrders();
        Map<OrderType,Long> orderTypeHashMap = orders.stream().collect(Collectors.groupingBy(Order::getOrder_type,Collectors.counting()));
        Long buyCount = orderTypeHashMap.getOrDefault(OrderType.BUY,0L);
        Long sellCount = orderTypeHashMap.getOrDefault(OrderType.SELL, 0L);
        orderTypeHashMap.put(OrderType.BUY, buyCount);
        orderTypeHashMap.put(OrderType.SELL, sellCount);
        return ResponseEntity.ok(orderTypeHashMap);
    }

    public ResponseEntity<String> topCustomerByVolume()
    {
        List<Order> orders = oc.getAllOrders();
        Map<UUID, Long> top_customer = orders.stream().collect(Collectors.groupingBy(Order::getMemberId,Collectors.counting()));
        Optional<Map.Entry<UUID, Long>> topCustomerEntry = top_customer.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue());
        if(topCustomerEntry.isPresent())
        {
            UUID memberId = topCustomerEntry.get().getKey();
            return ResponseEntity.ok("Top customer's memberId is: " + memberId + "and the number of orders are: " + topCustomerEntry.get().getValue());
        }

        else {
            return ResponseEntity.ok("No orders found");
        }
    }

    public ResponseEntity<Map<Status, Long>> groupByStatus()
    {
        Collection<Order> orders = oc.getAllOrders();
        Map<Status, Long> statusHashMap = orders.stream().collect(Collectors.groupingBy(Order::getStatus, Collectors.counting()));
        Long cancelledCount = statusHashMap.getOrDefault(Status.CANCELLED,0L);
        Long processingCount = statusHashMap.getOrDefault(Status.PROCESSING,0L);
        Long completedCount = statusHashMap.getOrDefault(Status.COMPLETED, 0L);
        statusHashMap.put(Status.COMPLETED, completedCount);
        statusHashMap.put(Status.PROCESSING, processingCount);
        statusHashMap.put(Status.CANCELLED, cancelledCount);

        return ResponseEntity.ok(statusHashMap);
    }

}
