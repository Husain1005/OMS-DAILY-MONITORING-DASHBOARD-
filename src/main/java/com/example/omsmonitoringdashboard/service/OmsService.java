package com.example.omsmonitoringdashboard.service;

import com.example.omsmonitoringdashboard.model.OrderCountRowset;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Base64;
import java.util.List;

@Service
public class OmsService {
    private final RestTemplate restTemplate;

    public OmsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${titan.order-count.url:https://titan-preprod-1.oms.supply-chain.ibm.com/smcfs/restapi/executeFlow/Titan_OrderCount}")
    private String titanOrderCountUrl;

    @Value("${titan.order-count.username:}")
    private String titanUsername;

    @Value("${titan.order-count.password:}")
    private String titanPassword;

    @SuppressWarnings("null")
    public OrderCountRowset getTitanOrderCount() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        headers.setAccept(List.of(MediaType.APPLICATION_XML));

        if (titanUsername != null && !titanUsername.isEmpty() && titanPassword != null && !titanPassword.isEmpty()) {
            String auth = titanUsername + ":" + titanPassword;
            String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
            headers.set("Authorization", "Basic " + encodedAuth);
        }

        String requestBody = "<Order />";
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(titanOrderCountUrl, HttpMethod.POST, entity, String.class);
            String payload = response.getBody();
            if (payload == null || payload.isBlank()) {
                throw new RuntimeException("Titan order count response was empty");
            }

            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            payload = payload.replaceFirst("^\\uFEFF", "");
            return xmlMapper.readValue(payload, OrderCountRowset.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch Titan order count: " + e.getMessage(), e);
        }
    }
}
