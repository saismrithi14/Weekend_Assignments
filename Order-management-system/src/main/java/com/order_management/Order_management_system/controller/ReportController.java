package com.order_management.Order_management_system.controller;

import com.order_management.Order_management_system.enums.*;
import com.order_management.Order_management_system.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

@RestController
@RequestMapping("/oms/reports")
public class ReportController {
    @Autowired
    ReportService reportService;

    @GetMapping("/total_amount")

    public ResponseEntity<String> getTotalAmount()
    {
        return reportService.getTotalAmount();
    }

    @GetMapping("/total-buy-vs-sell")
    public ResponseEntity<Map<OrderType, Long>> totalBuyVsSell()
    {
        return reportService.totalBuyVsSell();
    }

    @GetMapping("/top-customer")
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
