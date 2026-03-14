package ServiceReport.reports.controller;

import ServiceReport.reports.enums.OrderType;
import ServiceReport.reports.enums.Status;
import ServiceReport.reports.model.Order;
import ServiceReport.reports.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/oms/reports")
public class ReportController {
    @Autowired
    ReportService reportService;

    @GetMapping("/all")
    public List<Order> getAllOrders()
    {
        return reportService.getAllOrders();
    }

    @GetMapping("total-amount")
    public ResponseEntity<String> getTotalAmount()
    {
        return reportService.getTotalAmount();
    }

    @GetMapping("/total-buy-vs-sell")
    public ResponseEntity<Map<OrderType, Long>> totalBuyVsSell()
    {
        return reportService.totalBuyVsSell();
    }

    @GetMapping("/top-customer-by-value")
    public ResponseEntity<String> topCustomerByVolume()
    {
        return reportService.topCustomerByVolume();
    }

    @GetMapping("/group-by-status")
    public ResponseEntity<Map<Status, Long>> groupByStatus()
    {
        return reportService.groupByStatus();
    }
}
