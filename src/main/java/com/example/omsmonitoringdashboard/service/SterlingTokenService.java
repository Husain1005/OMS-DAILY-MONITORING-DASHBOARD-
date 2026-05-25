package com.example.omsmonitoringdashboard.service;

import com.fasterxml.jackson.databind.JsonNode;
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
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class SterlingTokenService {

    private static final Logger log = LoggerFactory.getLogger(SterlingTokenService.class);

    private final RestTemplate restTemplate;

    @Value("${titan.login.url:https://titan-preprod-1.oms.supply-chain.ibm.com/smcfs/restapi/invoke/login}")
    private String loginUrl;

    @Value("${titan.login.id:admin}")
    private String loginId;

    @Value("${titan.login.password:password}")
    private String loginPassword;

    @Value("${titan.token.expiry-minutes:25}")
    private long tokenExpiryMinutes;

    private volatile String cachedToken;
    private volatile Instant tokenCreatedAt;
    private final ReentrantLock tokenLock = new ReentrantLock();

    public SterlingTokenService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Token is fetched lazily on first use, not on startup,
     * so the app can start even if the Sterling server is unreachable.
     */
    public String getLoginId() {
        return loginId;
    }

    public String getToken() {
        if (isTokenExpired()) {
            refreshToken();
        }
        return cachedToken;
    }

    private boolean isTokenExpired() {
        return cachedToken == null || tokenCreatedAt == null
                || Instant.now().isAfter(tokenCreatedAt.plusSeconds(tokenExpiryMinutes * 60));
    }

    private void refreshToken() {
        tokenLock.lock();
        try {
            // Double-check after acquiring lock
            if (!isTokenExpired()) {
                return;
            }
            log.info("Requesting new Sterling token from {}", loginUrl);
            cachedToken = callLoginApi();
            tokenCreatedAt = Instant.now();
            log.info("Sterling token obtained successfully");
        } catch (Exception e) {
            log.error("Failed to obtain Sterling token", e);
            throw new RuntimeException("Failed to obtain Sterling login token: " + e.getMessage(), e);
        } finally {
            tokenLock.unlock();
        }
    }

    /**
     * Invalidate the current token so the next call to getToken() will refresh.
     */
    public void invalidateToken() {
        tokenLock.lock();
        try {
            cachedToken = null;
            tokenCreatedAt = null;
            log.info("Sterling token invalidated");
        } finally {
            tokenLock.unlock();
        }
    }

    private String callLoginApi() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        headers.setAccept(java.util.List.of(MediaType.APPLICATION_XML));

        String requestBody = "<Login LoginID=\"" + escapeXml(loginId)
                + "\" Password=\"" + escapeXml(loginPassword) + "\" />";

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(loginUrl, HttpMethod.POST, entity, String.class);
        log.info("Sterling login response status: {}", response.getStatusCodeValue());

        String body = response.getBody();
        if (body == null || body.isBlank()) {
            throw new RuntimeException("Sterling login response was empty");
        }

        // Parse the XML response to extract the UserToken
        try {
            body = body.replaceFirst("^\\uFEFF", "");
            XmlMapper xmlMapper = new XmlMapper();
            JsonNode root = xmlMapper.readTree(body);
            JsonNode tokenNode = root.get("UserToken");
            if (tokenNode == null || tokenNode.asText().isBlank()) {
                throw new RuntimeException("UserToken not found in Sterling login response: " + body);
            }
            return tokenNode.asText();
        } catch (RuntimeException re) {
            throw re;
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse Sterling login response: " + e.getMessage(), e);
        }
    }

    private static String escapeXml(String value) {
        if (value == null) return "";
        return value.replace("&", "&amp;")
                     .replace("<", "&lt;")
                     .replace(">", "&gt;")
                     .replace("\"", "&quot;")
                     .replace("'", "&apos;");
    }
}
