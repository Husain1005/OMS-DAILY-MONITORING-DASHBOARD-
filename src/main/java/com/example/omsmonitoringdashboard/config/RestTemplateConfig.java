package com.example.omsmonitoringdashboard.config;

import java.io.InputStream;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.socket.ConnectionSocketFactory;
import org.apache.hc.client5.http.socket.PlainConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.http.config.Registry;
import org.apache.hc.core5.http.config.RegistryBuilder;
import org.apache.hc.core5.ssl.SSLContexts;

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

    @Value("${titan.ssl.truststore-path}")
    private String truststorePath;

    @Value("${titan.ssl.truststore-password}")
    private String truststorePassword;

    private final ResourceLoader resourceLoader;

    public RestTemplateConfig(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Bean
    public RestTemplate restTemplate() throws Exception {

        // Load CLIENT CERTIFICATE (Keystore)
        KeyStore keyStore = KeyStore.getInstance("JKS"); // or "PKCS12" if .p12
        try (InputStream ksStream = resourceLoader.getResource(keystorePath).getInputStream()) {
            keyStore.load(ksStream, keystorePassword.toCharArray());
        }

        // Load TRUSTSTORE (Server cert)
        KeyStore trustStore = KeyStore.getInstance("JKS");
        try (InputStream tsStream = resourceLoader.getResource(truststorePath).getInputStream()) {
            trustStore.load(tsStream, truststorePassword.toCharArray());
        }

        // Build SSL Context (mTLS)
        SSLContext sslContext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, keystorePassword.toCharArray())   // client cert
                .loadTrustMaterial(trustStore, null)                         // trust server
                .build();

        // Required for HttpClient 5.5
        SSLConnectionSocketFactory sslSocketFactory =
                new SSLConnectionSocketFactory(sslContext);

        Registry<ConnectionSocketFactory> registry =
                RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("https", sslSocketFactory)
                        .register("http", new PlainConnectionSocketFactory())
                        .build();

        PoolingHttpClientConnectionManager connectionManager =
                new PoolingHttpClientConnectionManager(registry);

        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .build();

        return new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClient));
    }
}