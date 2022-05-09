package com.example.elastic.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.cat.NodesResponse;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author jacksparrow414
 * @date 2022/5/9
 */
@SpringBootTest
@Log
public class ElasticSearchConfigurationTest {

    @Autowired
    private ElasticsearchClient elasticsearchClient;
    
    @SneakyThrows
    @Test
    public void assertConnectElasticSearch() {
        NodesResponse nodes = elasticsearchClient.cat().nodes();
        assertEquals("cdfhilmrstw", nodes.valueBody().get(0).nodeRole());
    }
}