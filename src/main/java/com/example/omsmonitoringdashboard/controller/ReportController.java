package com.example.omsmonitoringdashboard.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;
import com.example.omsmonitoringdashboard.model.OrderCountRowset;
import com.example.omsmonitoringdashboard.service.OmsService;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final OmsService omsService;

    public ReportController(OmsService omsService) {
        this.omsService = omsService;
    }

    @GetMapping("/order-count")
    public List<Map<String, Object>> orderCount() {
        return omsService.getOrderCount();
    }

    @GetMapping("/hmtr-orders")
    public List<Map<String, Object>> hmtrOrders() {
        return omsService.getHmtrOrders();
    }

    @GetMapping("/backorder")
    public Map<String, String> backorder() {
        return omsService.getBackorder();
    }

    @GetMapping("/rejected-at-pos")
    public Map<String, String> rejectedAtPos() {
        return omsService.getRejectedAtPos();
    }

    @GetMapping("/manual-cancellation")
    public List<Map<String, Object>> manualCancellation() {
        return omsService.getManualCancellation();
    }

    @GetMapping("/fraud-check-hold")
    public List<Map<String, Object>> fraudCheckHold() {
        return omsService.getFraudCheckHold();
    }

    @GetMapping("/stuck-orders")
    public List<Map<String, Object>> stuckOrders() {
        return omsService.getStuckOrders();
    }

    @GetMapping("/egc-gc-orders")
    public List<Map<String, Object>> egcGcOrders() {
        return omsService.getEgcGcOrders();
    }

    @PostMapping("/titan/order-count")
    @GetMapping("/titan/order-count")
    public OrderCountRowset titanOrderCount() {
        return omsService.getTitanOrderCount();
    }
}
