@echo off
cd /d e:\Monitoring_Dashboard\OMS-DAILY-MONITORING-DASHBOARD-
(
echo package com.example.omsmonitoringdashboard.service;
echo.
echo import com.example.omsmonitoringdashboard.model.OrderCountRowset;
echo import org.springframework.beans.factory.annotation.Value;
echo import org.springframework.http.HttpEntity;
echo import org.springframework.http.HttpHeaders;
echo import org.springframework.http.HttpMethod;
echo import org.springframework.http.MediaType;
echo import org.springframework.http.ResponseEntity;
echo import org.springframework.stereotype.Service;
echo import org.springframework.web.client.RestTemplate;
echo import java.util.Base64;
echo import java.util.List;
echo.
echo @Service
echo public class OmsService {
echo     private final RestTemplate restTemplate = new RestTemplate^(^);
echo.
echo     @Value^("${titan.order-count.url:https://titan-preprod-1.oms.supply-chain.ibm.com/smcfs/restapi/executeFlow/Titan_OrderCount}"^)
echo     private String titanOrderCountUrl;
echo.
echo     @Value^("${titan.order-count.username:}"^)
echo     private String titanUsername;
echo.
echo     @Value^("${titan.order-count.password:}"^)
echo     private String titanPassword;
echo.
echo     public OrderCountRowset getTitanOrderCount^(^) {
echo         HttpHeaders headers = new HttpHeaders^(^);
echo         headers.setContentType^(MediaType.APPLICATION_XML^);
echo         headers.setAccept^(List.of^(MediaType.APPLICATION_XML^)^);
echo.
echo         if ^(titanUsername != null ^&^& !titanUsername.isEmpty^(^) ^&^& titanPassword != null ^&^& !titanPassword.isEmpty^(^)^) {
echo             String auth = titanUsername + ":" + titanPassword;
echo             String encodedAuth = Base64.getEncoder^(^).encodeToString^(auth.getBytes^(^)^);
echo             headers.set^("Authorization", "Basic " + encodedAuth^);
echo         }
echo.
echo         String requestBody = "^<Order /^>";
echo         HttpEntity^<String^> entity = new HttpEntity^<^>^(requestBody, headers^);
echo.
echo         try {
echo             ResponseEntity^<OrderCountRowset^> response = restTemplate.exchange^(titanOrderCountUrl, HttpMethod.POST, entity, OrderCountRowset.class^);
echo             return response.getBody^(^);
echo         } catch ^(Exception e^) {
echo             throw new RuntimeException^("Failed to fetch Titan order count: " + e.getMessage^(^), e^);
echo         }
echo     }
echo }
) > service\OmsService_new.java
move service\OmsService_new.java service\OmsService.java