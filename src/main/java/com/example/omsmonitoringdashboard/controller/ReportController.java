package com.example.omsmonitoringdashboard.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.omsmonitoringdashboard.model.OrderCountRowset;
import com.example.omsmonitoringdashboard.service.OmsService;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    private final OmsService omsService;
    
    public ReportController(OmsService omsService) {
        this.omsService = omsService;
    }
    
    @PostMapping("/titan/order-count")
    @GetMapping("/titan/order-count")
    public OrderCountRowset titanOrderCount() {
        return omsService.getTitanOrderCount();
    }
}