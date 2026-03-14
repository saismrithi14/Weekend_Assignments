package ServiceReport.reports.client;

import ServiceReport.reports.model.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@FeignClient(name="order-service", url="http://localhost:8082")
public interface orderClient {

    @GetMapping("/oms/orders/all")
    List<Order> getAllOrders();

}
