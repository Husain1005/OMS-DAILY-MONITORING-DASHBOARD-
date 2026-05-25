package com.example.omsmonitoringdashboard.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.omsmonitoringdashboard.model.BackorderRowset;
import com.example.omsmonitoringdashboard.model.EgcGcOrderRowset;
import com.example.omsmonitoringdashboard.model.FraudHoldRowset;
import com.example.omsmonitoringdashboard.model.HmtrOrderRowset;
import com.example.omsmonitoringdashboard.model.ManualCancellationRowset;
import com.example.omsmonitoringdashboard.model.OrderCountRowset;
import com.example.omsmonitoringdashboard.model.RejectedAtPosRowset;
import com.example.omsmonitoringdashboard.model.StuckOrderRowset;
import com.example.omsmonitoringdashboard.service.OmsService;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    private final OmsService omsService;
    
    public ReportController(OmsService omsService) {
        this.omsService = omsService;
    }
    @GetMapping("/titan/order-count")
    public OrderCountRowset titanOrderCount() {
        return omsService.getTitanOrderCount();
    }
    @GetMapping("/titan/manual-cancellation")
    public ManualCancellationRowset manualCancellationReport() {
        return omsService.getManualCancellationReport();
    }

    @GetMapping("/titan/backorder")
    public BackorderRowset backorderReport() {
        return omsService.getBackorderReport();
    }

    @GetMapping("/titan/rejected-at-pos")
    public RejectedAtPosRowset rejectedAtPosReport() {
        return omsService.getRejectedAtPosReport();
    }

    @GetMapping("/titan/fraud-hold")
    public FraudHoldRowset fraudHoldReport() {
        return omsService.getFraudHoldReport();
    }

    @GetMapping("/titan/stuck-order")
    public StuckOrderRowset stuckOrderReport() {
        return omsService.getStuckOrderReport();
    }

    @GetMapping("/titan/egc-gc-orders")
    public EgcGcOrderRowset egcGcOrderReport() {
        return omsService.getEgcGcOrderReport();
    }

    @GetMapping("/titan/hmtr-orders")
    public HmtrOrderRowset hmtrOrdersReport() {
        return omsService.getHmtrOrdersReport();
    }
}