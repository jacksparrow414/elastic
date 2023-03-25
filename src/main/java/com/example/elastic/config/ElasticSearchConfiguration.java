package com.example.elastic.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import javax.net.ssl.SSLContext;
import lombok.SneakyThrows;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jacksparrow414
 * @date 2022/5/9
 */
@Configuration
public class ElasticSearchConfiguration {
    
    @SneakyThrows
    @Bean
    public ElasticsearchClient elasticsearchClient() {
        Path caCertificatePath = Path.of(Thread.currentThread().getContextClassLoader().getResource("ca/ca.crt").toURI());
        CertificateFactory factory =
            CertificateFactory.getInstance("X.509");
        Certificate trustedCa;
        try (InputStream is = Files.newInputStream(caCertificatePath)) {
            trustedCa = factory.generateCertificate(is);
        }
        KeyStore trustStore = KeyStore.getInstance("pkcs12");
        trustStore.load(null, null);
        trustStore.setCertificateEntry("ca", trustedCa);
        SSLContextBuilder sslContextBuilder = SSLContexts.custom()
            .loadTrustMaterial(trustStore, null);
        final SSLContext sslContext = sslContextBuilder.build();
        CredentialsProvider credentialsProvider =
            new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
            new UsernamePasswordCredentials("elastic", "changeme"));
        RestClient restClient = RestClient.builder(
            new HttpHost("localhost", 19200, "https"),
            new HttpHost("localhost", 19201, "https"),
            new HttpHost("localhost", 19202, "https"))
            .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder.setSSLContext(sslContext)
                .setDefaultCredentialsProvider(credentialsProvider)).build();
        ElasticsearchTransport elasticsearchTransport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        return new ElasticsearchClient(elasticsearchTransport);
    }
}
