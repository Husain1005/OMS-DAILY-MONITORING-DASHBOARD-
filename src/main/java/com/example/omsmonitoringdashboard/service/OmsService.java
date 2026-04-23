package com.example.omsmonitoringdashboard.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class OmsService {

    private final RestTemplate restTemplate;

    @Value("${oms.base-url}")
    private String baseUrl;

    @Value("${oms.auth-url}")
    private String authUrl;

    @Value("${oms.username}")
    private String username;

    @Value("${oms.password}")
    private String password;

    @Value("${oms.client-id}")
    private String clientId;

    @Value("${oms.client-secret}")
    private String clientSecret;

    private String accessToken;

    public OmsService() {
        this.restTemplate = new RestTemplate();
    }

    private String getAccessToken() {
        if (accessToken != null) {
            return accessToken;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "password");
        formData.add("username", username);
        formData.add("password", password);
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(formData, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(authUrl + "/oauth/token", request, Map.class);
        accessToken = (String) response.getBody().get("access_token");
        return accessToken;
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(getAccessToken());
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return headers;
    }

    public List<Map<String, Object>> getOrderCount() {
        HttpEntity<?> entity = new HttpEntity<>(createHeaders());
        ResponseEntity<List> response = restTemplate.exchange(baseUrl + "/reports/order-count", HttpMethod.GET, entity, List.class);
        return response.getBody();
    }

    public List<Map<String, Object>> getHmtrOrders() {
        HttpEntity<?> entity = new HttpEntity<>(createHeaders());
        ResponseEntity<List> response = restTemplate.exchange(baseUrl + "/reports/hmtr-orders", HttpMethod.GET, entity, List.class);
        return response.getBody();
    }

    public Map<String, String> getBackorder() {
        HttpEntity<?> entity = new HttpEntity<>(createHeaders());
        ResponseEntity<Map> response = restTemplate.exchange(baseUrl + "/reports/backorder", HttpMethod.GET, entity, Map.class);
        return response.getBody();
    }

    public Map<String, String> getRejectedAtPos() {
        HttpEntity<?> entity = new HttpEntity<>(createHeaders());
        ResponseEntity<Map> response = restTemplate.exchange(baseUrl + "/reports/rejected-at-pos", HttpMethod.GET, entity, Map.class);
        return response.getBody();
    }

    public List<Map<String, Object>> getManualCancellation() {
        HttpEntity<?> entity = new HttpEntity<>(createHeaders());
        ResponseEntity<List> response = restTemplate.exchange(baseUrl + "/reports/manual-cancellation", HttpMethod.GET, entity, List.class);
        return response.getBody();
    }

    public List<Map<String, Object>> getFraudCheckHold() {
        HttpEntity<?> entity = new HttpEntity<>(createHeaders());
        ResponseEntity<List> response = restTemplate.exchange(baseUrl + "/reports/fraud-check-hold", HttpMethod.GET, entity, List.class);
        return response.getBody();
    }

    public List<Map<String, Object>> getStuckOrders() {
        HttpEntity<?> entity = new HttpEntity<>(createHeaders());
        ResponseEntity<List> response = restTemplate.exchange(baseUrl + "/reports/stuck-orders", HttpMethod.GET, entity, List.class);
        return response.getBody();
    }

    public List<Map<String, Object>> getEgcGcOrders() {
        HttpEntity<?> entity = new HttpEntity<>(createHeaders());
        ResponseEntity<List> response = restTemplate.exchange(baseUrl + "/reports/egc-gc-orders", HttpMethod.GET, entity, List.class);
        return response.getBody();
    }
}