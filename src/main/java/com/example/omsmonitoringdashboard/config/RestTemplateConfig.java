package com.example.omsmonitoringdashboard.config;

import java.io.InputStream;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Value("${titan.ssl.keystore-path}")
    private String keystorePath;

    @Value("${titan.ssl.keystore-password}")
    private String keystorePassword;

    private final ResourceLoader resourceLoader;

    public RestTemplateConfig(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Bean
    public RestTemplate restTemplate() throws Exception {

        KeyStore keyStore = KeyStore.getInstance("PKCS12");

        try (InputStream is =
                     resourceLoader.getResource(keystorePath).getInputStream()) {
            keyStore.load(is, keystorePassword.toCharArray());
        }

        SSLContext sslContext = SSLContextBuilder.create()
                .loadKeyMaterial(keyStore, keystorePassword.toCharArray())
                .loadTrustMaterial(keyStore, null)
                .build();

        SSLConnectionSocketFactory socketFactory =
                new SSLConnectionSocketFactory(sslContext);

        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(
                        PoolingHttpClientConnectionManagerBuilder.create()
                                .setSSLSocketFactory(socketFactory)
                                .build()
                )
                .build();

        return new RestTemplate(
                new HttpComponentsClientHttpRequestFactory(httpClient)
        );
    }
}