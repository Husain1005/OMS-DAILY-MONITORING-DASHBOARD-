package com.example.omsmonitoringdashboard.service;

import com.example.omsmonitoringdashboard.model.BackorderRowset;
import com.example.omsmonitoringdashboard.model.EgcGcOrderRowset;
import com.example.omsmonitoringdashboard.model.FraudHoldRowset;
import com.example.omsmonitoringdashboard.model.HmtrOrderRowset;
import com.example.omsmonitoringdashboard.model.ManualCancellationRowset;
import com.example.omsmonitoringdashboard.model.OrderCountRowset;
import com.example.omsmonitoringdashboard.model.RejectedAtPosRowset;
import com.example.omsmonitoringdashboard.model.StuckOrderRowset;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@Service
public class OmsService {

    private static final Logger log = LoggerFactory.getLogger(OmsService.class);
    private final RestTemplate restTemplate;
    private final SterlingTokenService sterlingTokenService;

    public OmsService(RestTemplate restTemplate, SterlingTokenService sterlingTokenService) {
        this.restTemplate = restTemplate;
        this.sterlingTokenService = sterlingTokenService;
    }

    @Value("${titan.order-count.url:https://titan-preprod-1.oms.supply-chain.ibm.com/smcfs/restapi/executeFlow/Titan_OrderCount}")
    private String titanOrderCountUrl;

    @Value("${titan.manual-cancellation.url:https://titan-preprod-1.oms.supply-chain.ibm.com/smcfs/restapi/executeFlow/Titan_ManualCancellation}")
    private String titanManualCancellationUrl;

    @Value("${titan.backorder.url:https://titan-preprod-1.oms.supply-chain.ibm.com/smcfs/restapi/executeFlow/Titan_Backorder}")
    private String titanBackorderUrl;

    @Value("${titan.rejected-at-pos.url:https://titan-preprod-1.oms.supply-chain.ibm.com/smcfs/restapi/executeFlow/Titan_RejectedAtPoss}")
    private String titanRejectedAtPosUrl;

    @Value("${titan.fraud-hold.url:https://titan-preprod-1.oms.supply-chain.ibm.com/smcfs/restapi/executeFlow/Titan_FraudHold_Orders}")
    private String titanFraudHoldUrl;

    @Value("${titan.stuck-order.url:https://titan-preprod-1.oms.supply-chain.ibm.com/smcfs/restapi/executeFlow/Titan_Stuck_Order}")
    private String titanStuckOrderUrl;

    @Value("${titan.hmtr-orders.url:https://titan-preprod-1.oms.supply-chain.ibm.com/smcfs/restapi/executeFlow/Titan_MTR_Orders}")
    private String titanHmtrOrdersUrl;

    @Value("${titan.egc-gc-order.url:https://titan-preprod-1.oms.supply-chain.ibm.com/smcfs/restapi/executeFlow/Titan_EGC_GC_Order}")
    private String titanEgcGcOrderUrl;

    @SuppressWarnings("null")
    public OrderCountRowset getTitanOrderCount() {
        try {
            return callTitanOrderCount();
        } catch (HttpClientErrorException.Unauthorized | HttpClientErrorException.Forbidden ex) {
            log.warn("Token may have expired ({}). Refreshing and retrying...", ex.getStatusCode());
            sterlingTokenService.invalidateToken();
            return callTitanOrderCount();
        }
    }

    @SuppressWarnings("null")
    public ManualCancellationRowset getManualCancellationReport() {
        try {
            return callManualCancellation();
        } catch (HttpClientErrorException.Unauthorized | HttpClientErrorException.Forbidden ex) {
            log.warn("Token may have expired ({}). Refreshing and retrying...", ex.getStatusCode());
            sterlingTokenService.invalidateToken();
            return callManualCancellation();
        }
    }

    private ManualCancellationRowset callManualCancellation() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        headers.setAccept(List.of(MediaType.APPLICATION_XML));

        String requestBody = "<Order />";
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        String urlWithToken = titanManualCancellationUrl
                + "?_loginid=" + sterlingTokenService.getLoginId()
                + "&_token=" + sterlingTokenService.getToken();

        try {
            log.info("Fetching Manual Cancellation report from {}", titanManualCancellationUrl);
            ResponseEntity<String> response = restTemplate.exchange(urlWithToken, HttpMethod.POST, entity, String.class);
            log.info("Manual Cancellation response status: {}", response.getStatusCodeValue());
            String payload = response.getBody();
            if (payload == null || payload.isBlank()) {
                throw new RuntimeException("Manual Cancellation response was empty");
            }

            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            payload = payload.replaceFirst("^\\uFEFF", "");
            return xmlMapper.readValue(payload, ManualCancellationRowset.class);
        } catch (HttpClientErrorException e) {
            throw e;
        } catch (Exception e) {
            log.error("Failed to fetch Manual Cancellation report", e);
            throw new RuntimeException("Failed to fetch Manual Cancellation report: " + e.getMessage(), e);
        }
    }

    private OrderCountRowset callTitanOrderCount() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        headers.setAccept(List.of(MediaType.APPLICATION_XML));

        String requestBody = "<Order />";
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        String urlWithToken = titanOrderCountUrl
                + "?_loginid=" + sterlingTokenService.getLoginId()
                + "&_token=" + sterlingTokenService.getToken();

        try {
            log.info("Fetching Titan order count from {}", titanOrderCountUrl);
            ResponseEntity<String> response = restTemplate.exchange(urlWithToken, HttpMethod.POST, entity, String.class);
            log.info("Titan order count response status: {}", response.getStatusCodeValue());
            String payload = response.getBody();
            if (payload == null || payload.isBlank()) {
                throw new RuntimeException("Titan order count response was empty");
            }

            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            payload = payload.replaceFirst("^\\uFEFF", "");
            return xmlMapper.readValue(payload, OrderCountRowset.class);
        } catch (HttpClientErrorException e) {
            throw e;
        } catch (Exception e) {
            log.error("Failed to fetch Titan order count", e);
            throw new RuntimeException("Failed to fetch Titan order count: " + e.getMessage(), e);
        }
    }

    @SuppressWarnings("null")
    public BackorderRowset getBackorderReport() {
        try {
            return callBackorder();
        } catch (HttpClientErrorException.Unauthorized | HttpClientErrorException.Forbidden ex) {
            log.warn("Token may have expired ({}). Refreshing and retrying...", ex.getStatusCode());
            sterlingTokenService.invalidateToken();
            return callBackorder();
        }
    }

    private BackorderRowset callBackorder() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        headers.setAccept(List.of(MediaType.APPLICATION_XML));

        String requestBody = "<Order />";
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        String urlWithToken = titanBackorderUrl
                + "?_loginid=" + sterlingTokenService.getLoginId()
                + "&_token=" + sterlingTokenService.getToken();

        try {
            log.info("Fetching Backorder report from {}", titanBackorderUrl);
            ResponseEntity<String> response = restTemplate.exchange(urlWithToken, HttpMethod.POST, entity, String.class);
            log.info("Backorder response status: {}", response.getStatusCodeValue());
            String payload = response.getBody();
            if (payload == null || payload.isBlank()) {
                throw new RuntimeException("Backorder response was empty");
            }

            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            payload = payload.replaceFirst("^\\uFEFF", "");
            return xmlMapper.readValue(payload, BackorderRowset.class);
        } catch (HttpClientErrorException e) {
            throw e;
        } catch (Exception e) {
            log.error("Failed to fetch Backorder report", e);
            throw new RuntimeException("Failed to fetch Backorder report: " + e.getMessage(), e);
        }
    }

    @SuppressWarnings("null")
    public RejectedAtPosRowset getRejectedAtPosReport() {
        try {
            return callRejectedAtPos();
        } catch (HttpClientErrorException.Unauthorized | HttpClientErrorException.Forbidden ex) {
            log.warn("Token may have expired ({}). Refreshing and retrying...", ex.getStatusCode());
            sterlingTokenService.invalidateToken();
            return callRejectedAtPos();
        }
    }

    private RejectedAtPosRowset callRejectedAtPos() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        headers.setAccept(List.of(MediaType.APPLICATION_XML));

        String requestBody = "<Order />";
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        String urlWithToken = titanRejectedAtPosUrl
                + "?_loginid=" + sterlingTokenService.getLoginId()
                + "&_token=" + sterlingTokenService.getToken();

        try {
            log.info("Fetching Rejected at POS report from {}", titanRejectedAtPosUrl);
            ResponseEntity<String> response = restTemplate.exchange(urlWithToken, HttpMethod.POST, entity, String.class);
            log.info("Rejected at POS response status: {}", response.getStatusCodeValue());
            String payload = response.getBody();
            if (payload == null || payload.isBlank()) {
                throw new RuntimeException("Rejected at POS response was empty");
            }

            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            payload = payload.replaceFirst("^\\uFEFF", "");
            return xmlMapper.readValue(payload, RejectedAtPosRowset.class);
        } catch (HttpClientErrorException e) {
            throw e;
        } catch (Exception e) {
            log.error("Failed to fetch Rejected at POS report", e);
            throw new RuntimeException("Failed to fetch Rejected at POS report: " + e.getMessage(), e);
        }
    }

    @SuppressWarnings("null")
    public FraudHoldRowset getFraudHoldReport() {
        try {
            return callFraudHold();
        } catch (HttpClientErrorException.Unauthorized | HttpClientErrorException.Forbidden ex) {
            log.warn("Token may have expired ({}). Refreshing and retrying...", ex.getStatusCode());
            sterlingTokenService.invalidateToken();
            return callFraudHold();
        }
    }

    private FraudHoldRowset callFraudHold() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        headers.setAccept(List.of(MediaType.APPLICATION_XML));

        String requestBody = "<Order />";
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        String urlWithToken = titanFraudHoldUrl
                + "?_loginid=" + sterlingTokenService.getLoginId()
                + "&_token=" + sterlingTokenService.getToken();

        try {
            log.info("Fetching Fraud Hold Orders from {}", titanFraudHoldUrl);
            ResponseEntity<String> response = restTemplate.exchange(urlWithToken, HttpMethod.POST, entity, String.class);
            log.info("Fraud Hold Orders response status: {}", response.getStatusCodeValue());
            String payload = response.getBody();
            if (payload == null || payload.isBlank()) {
                throw new RuntimeException("Fraud Hold Orders response was empty");
            }

            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            payload = payload.replaceFirst("^\\uFEFF", "");
            return xmlMapper.readValue(payload, FraudHoldRowset.class);
        } catch (HttpClientErrorException e) {
            throw e;
        } catch (Exception e) {
            log.error("Failed to fetch Fraud Hold Orders", e);
            throw new RuntimeException("Failed to fetch Fraud Hold Orders: " + e.getMessage(), e);
        }
    }

    @SuppressWarnings("null")
    public StuckOrderRowset getStuckOrderReport() {
        try {
            return callStuckOrder();
        } catch (HttpClientErrorException.Unauthorized | HttpClientErrorException.Forbidden ex) {
            log.warn("Token may have expired ({}). Refreshing and retrying...", ex.getStatusCode());
            sterlingTokenService.invalidateToken();
            return callStuckOrder();
        }
    }

    private StuckOrderRowset callStuckOrder() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        headers.setAccept(List.of(MediaType.APPLICATION_XML));

        String requestBody = "<Order />";
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        String urlWithToken = titanStuckOrderUrl
                + "?_loginid=" + sterlingTokenService.getLoginId()
                + "&_token=" + sterlingTokenService.getToken();

        try {
            log.info("Fetching Stuck Orders from {}", titanStuckOrderUrl);
            ResponseEntity<String> response = restTemplate.exchange(urlWithToken, HttpMethod.POST, entity, String.class);
            log.info("Stuck Orders response status: {}", response.getStatusCodeValue());
            String payload = response.getBody();
            if (payload == null || payload.isBlank()) {
                throw new RuntimeException("Stuck Orders response was empty");
            }

            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            payload = payload.replaceFirst("^\\uFEFF", "");
            return xmlMapper.readValue(payload, StuckOrderRowset.class);
        } catch (HttpClientErrorException e) {
            throw e;
        } catch (Exception e) {
            log.error("Failed to fetch Stuck Orders", e);
            throw new RuntimeException("Failed to fetch Stuck Orders: " + e.getMessage(), e);
        }
    }

    @SuppressWarnings("null")
    public EgcGcOrderRowset getEgcGcOrderReport() {
        try {
            return callEgcGcOrder();
        } catch (HttpClientErrorException.Unauthorized | HttpClientErrorException.Forbidden ex) {
            log.warn("Token may have expired ({}). Refreshing and retrying...", ex.getStatusCode());
            sterlingTokenService.invalidateToken();
            return callEgcGcOrder();
        }
    }

    private EgcGcOrderRowset callEgcGcOrder() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        headers.setAccept(List.of(MediaType.APPLICATION_XML));

        String requestBody = "<Order />";
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        String urlWithToken = titanEgcGcOrderUrl
                + "?_loginid=" + sterlingTokenService.getLoginId()
                + "&_token=" + sterlingTokenService.getToken();

        try {
            log.info("Fetching EGC/GC Orders from {}", titanEgcGcOrderUrl);
            ResponseEntity<String> response = restTemplate.exchange(urlWithToken, HttpMethod.POST, entity, String.class);
            log.info("EGC/GC Orders response status: {}", response.getStatusCodeValue());
            String payload = response.getBody();
            if (payload == null || payload.isBlank()) {
                throw new RuntimeException("EGC/GC Orders response was empty");
            }

            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            payload = payload.replaceFirst("^\\uFEFF", "");
            return xmlMapper.readValue(payload, EgcGcOrderRowset.class);
        } catch (HttpClientErrorException e) {
            throw e;
        } catch (Exception e) {
            log.error("Failed to fetch EGC/GC Orders", e);
            throw new RuntimeException("Failed to fetch EGC/GC Orders: " + e.getMessage(), e);
        }
    }

    @SuppressWarnings("null")
    public HmtrOrderRowset getHmtrOrdersReport() {
        try {
            return callHmtrOrders();
        } catch (HttpClientErrorException.Unauthorized | HttpClientErrorException.Forbidden ex) {
            log.warn("Token may have expired ({}). Refreshing and retrying...", ex.getStatusCode());
            sterlingTokenService.invalidateToken();
            return callHmtrOrders();
        }
    }

    private HmtrOrderRowset callHmtrOrders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        headers.setAccept(List.of(MediaType.APPLICATION_XML));

        String requestBody = "<Order />";
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        String urlWithToken = titanHmtrOrdersUrl
                + "?_loginid=" + sterlingTokenService.getLoginId()
                + "&_token=" + sterlingTokenService.getToken();

        try {
            log.info("Fetching HMTR Orders from {}", titanHmtrOrdersUrl);
            ResponseEntity<String> response = restTemplate.exchange(urlWithToken, HttpMethod.POST, entity, String.class);
            log.info("HMTR Orders response status: {}", response.getStatusCodeValue());
            String payload = response.getBody();
            if (payload == null || payload.isBlank()) {
                throw new RuntimeException("HMTR Orders response was empty");
            }

            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            payload = payload.replaceFirst("^\\uFEFF", "");
            return xmlMapper.readValue(payload, HmtrOrderRowset.class);
        } catch (HttpClientErrorException e) {
            throw e;
        } catch (Exception e) {
            log.error("Failed to fetch HMTR Orders", e);
            throw new RuntimeException("Failed to fetch HMTR Orders: " + e.getMessage(), e);
        }
    }
}
