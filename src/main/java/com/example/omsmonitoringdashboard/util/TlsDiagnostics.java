package com.example.omsmonitoringdashboard.util;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

@Component
public class TlsDiagnostics {

    private final ResourceLoader resourceLoader;

    public TlsDiagnostics(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public void diagnosticsTruststoreAndKeystore(
            String keystorePath,
            String keystorePassword,
            String truststorePath,
            String truststorePassword) {
        System.out.println("\n========== TLS DIAGNOSTICS ==========");
        try {
            diagnosticsKeystoreInternal(keystorePath, keystorePassword);
            diagnosticsTruststoreInternal(truststorePath, truststorePassword);
        } catch (Exception e) {
            System.err.println("Diagnostics failed: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("====================================\n");
    }

    private void diagnosticsKeystoreInternal(String keystorePath, String keystorePassword)
            throws Exception {
        System.out.println("\n--- KEYSTORE DIAGNOSTICS ---");
        System.out.println("Path: " + keystorePath);

        KeyStore keyStore = tryLoadKeyStore(keystorePath, keystorePassword, "PKCS12", "JKS");
        if (keyStore == null) {
            System.err.println("FAILED to load keystore");
            return;
        }

        System.out.println("Type: " + keyStore.getType());
        System.out.println("Size: " + keyStore.size() + " entries");

        Enumeration<String> aliases = keyStore.aliases();
        int aliasCount = 0;
        while (aliases.hasMoreElements()) {
            String alias = aliases.nextElement();
            aliasCount++;
            System.out.println("\n  Alias " + aliasCount + ": " + alias);
            System.out.println("    isKeyEntry: " + keyStore.isKeyEntry(alias));

            if (keyStore.isCertificateEntry(alias)) {
                Certificate cert = keyStore.getCertificate(alias);
                if (cert instanceof X509Certificate) {
                    X509Certificate x509 = (X509Certificate) cert;
                    System.out.println("    Subject: " + x509.getSubjectDN());
                    System.out.println("    Issuer: " + x509.getIssuerDN());
                    System.out.println("    NotBefore: " + x509.getNotBefore());
                    System.out.println("    NotAfter: " + x509.getNotAfter());
                    System.out.println("    SerialNumber: " + x509.getSerialNumber());
                }
            }

            // If it's a key entry, get the certificate chain
            if (keyStore.isKeyEntry(alias)) {
                Certificate[] certChain = keyStore.getCertificateChain(alias);
                if (certChain != null) {
                    System.out.println("    Certificate Chain: " + certChain.length + " certs");
                    for (int i = 0; i < certChain.length; i++) {
                        if (certChain[i] instanceof X509Certificate) {
                            X509Certificate x509 = (X509Certificate) certChain[i];
                            System.out.println("      Cert[" + i + "]: " + x509.getSubjectDN());
                            System.out.println("        Issuer: " + x509.getIssuerDN());
                            System.out.println("        NotAfter: " + x509.getNotAfter());
                        }
                    }
                }
            }
        }
    }

    private void diagnosticsTruststoreInternal(String truststorePath, String truststorePassword)
            throws Exception {
        System.out.println("\n--- TRUSTSTORE DIAGNOSTICS ---");
        System.out.println("Path: " + truststorePath);

        KeyStore trustStore = tryLoadKeyStore(truststorePath, truststorePassword, "PKCS12", "JKS");
        if (trustStore == null) {
            System.err.println("FAILED to load truststore");
            return;
        }

        System.out.println("Type: " + trustStore.getType());
        System.out.println("Size: " + trustStore.size() + " entries");

        Enumeration<String> aliases = trustStore.aliases();
        int aliasCount = 0;
        while (aliases.hasMoreElements()) {
            String alias = aliases.nextElement();
            aliasCount++;
            System.out.println("\n  Alias " + aliasCount + ": " + alias);
            System.out.println("    isCertificateEntry: " + trustStore.isCertificateEntry(alias));
            System.out.println("    isKeyEntry: " + trustStore.isKeyEntry(alias));

            Certificate cert = trustStore.getCertificate(alias);
            if (cert instanceof X509Certificate) {
                X509Certificate x509 = (X509Certificate) cert;
                System.out.println("    Subject: " + x509.getSubjectDN());
                System.out.println("    Issuer: " + x509.getIssuerDN());
                System.out.println("    NotBefore: " + x509.getNotBefore());
                System.out.println("    NotAfter: " + x509.getNotAfter());
                System.out.println("    SerialNumber: " + x509.getSerialNumber());
                System.out.println("    KeyUsage: " + formatKeyUsage(x509.getKeyUsage()));
            }
        }
    }

    private KeyStore tryLoadKeyStore(String path, String password, String... types)
            throws Exception {
        Exception lastException = null;
        for (String type : types) {
            try (InputStream stream = resourceLoader.getResource(path).getInputStream()) {
                KeyStore keyStore = KeyStore.getInstance(type);
                keyStore.load(stream, password.toCharArray());
                return keyStore;
            } catch (Exception e) {
                lastException = e;
            }
        }
        throw lastException;
    }

    private String formatKeyUsage(boolean[] keyUsage) {
        if (keyUsage == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder();
        String[] names = {
            "digitalSignature",
            "nonRepudiation",
            "keyEncipherment",
            "dataEncipherment",
            "keyAgreement",
            "keyCertSign",
            "cRLSign",
            "encipherOnly",
            "decipherOnly"
        };
        for (int i = 0; i < keyUsage.length && i < names.length; i++) {
            if (keyUsage[i]) {
                if (sb.length() > 0)
                    sb.append(", ");
                sb.append(names[i]);
            }
        }
        return sb.length() > 0 ? sb.toString() : "none";
    }
}
