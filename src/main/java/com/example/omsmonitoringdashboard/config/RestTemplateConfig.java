package com.example.omsmonitoringdashboard.config;

import java.io.InputStream;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;

import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.ssl.ClientTlsStrategyBuilder;
import org.apache.hc.client5.http.ssl.TlsSocketStrategy;
import org.apache.hc.core5.http.ssl.TLS;
import org.apache.hc.core5.ssl.SSLContexts;
import org.apache.hc.core5.util.Timeout;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.example.omsmonitoringdashboard.util.TlsDiagnostics;

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
    private final TlsDiagnostics tlsDiagnostics;

    public RestTemplateConfig(ResourceLoader resourceLoader, TlsDiagnostics tlsDiagnostics) {
        this.resourceLoader = resourceLoader;
        this.tlsDiagnostics = tlsDiagnostics;
    }

    @Bean
    public RestTemplate restTemplate() throws Exception {

        System.out.println("========== BUILDING MTLS REST TEMPLATE ==========");
        System.out.println("Keystore path: " + keystorePath);
        System.out.println("Truststore path: " + truststorePath);

        tlsDiagnostics.diagnosticsTruststoreAndKeystore(keystorePath, keystorePassword, truststorePath, truststorePassword);

        KeyStore keyStore = loadKeyStore(keystorePath, keystorePassword, "PKCS12", "JKS");
        System.out.println("Keystore path: " + keystorePath + " (type=" + keyStore.getType() + ")");
        System.out.println("Keystore loaded successfully. Aliases:");
        var aliases = keyStore.aliases();
        while (aliases.hasMoreElements()) {
            String alias = aliases.nextElement();
            System.out.println("  " + alias + " -> isKeyEntry: " + keyStore.isKeyEntry(alias));
        }

        KeyStore trustStore = loadKeyStore(truststorePath, truststorePassword, "PKCS12", "JKS");
        System.out.println("Truststore path: " + truststorePath + " (type=" + trustStore.getType() + ")");
        System.out.println("Truststore loaded successfully. Entries: " + trustStore.size());

        SSLContext sslContext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, keystorePassword.toCharArray(),
                        (aliasesMap, socket) -> aliasesMap.keySet().stream().findFirst().orElse(null))
                .loadTrustMaterial(trustStore, null)
                .build();

        System.out.println("SSLContext protocol: " + sslContext.getProtocol());
        System.out.println("========== MTLS REST TEMPLATE READY ==========");

        TlsSocketStrategy tlsStrategy = ClientTlsStrategyBuilder.create()
                .setSslContext(sslContext)
                .setTlsVersions(TLS.V_1_3, TLS.V_1_2)
                .buildClassic();

        PoolingHttpClientConnectionManager connectionManager =
                PoolingHttpClientConnectionManagerBuilder.create()
                        .setTlsSocketStrategy(tlsStrategy)
                        .build();

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(Timeout.ofSeconds(15))
                .setResponseTimeout(Timeout.ofSeconds(30))
                .setConnectionRequestTimeout(Timeout.ofSeconds(15))
                .build();

        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .setDefaultRequestConfig(requestConfig)
                .build();

        return new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClient));
    }

    private KeyStore loadKeyStore(String path, String password, String... types) throws Exception {
        Exception lastException = null;
        org.springframework.core.io.Resource resource = resolveResource(path);

        for (String type : types) {
            try (InputStream stream = resource.getInputStream()) {
                KeyStore keyStore = KeyStore.getInstance(type);
                keyStore.load(stream, password.toCharArray());
                return keyStore;
            } catch (Exception e) {
                lastException = e;
            }
        }
        throw lastException;
    }

    private org.springframework.core.io.Resource resolveResource(String path) {
        if (path == null || path.isBlank()) {
            throw new IllegalArgumentException("Keystore/truststore path must not be null or blank");
        }

        org.springframework.core.io.Resource resource = resourceLoader.getResource(path);
        if (!resource.exists()) {
            java.io.File file = new java.io.File(path);
            if (file.exists()) {
                resource = new org.springframework.core.io.FileSystemResource(file);
            }
        }
        return resource;
    }
}
