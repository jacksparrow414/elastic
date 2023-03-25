package com.example.elastic.controller;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import com.example.elastic.config.ElasticSearchConfiguration;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jacksparrow414
 * @date 2023/3/25
 */
@RestController
@RequestMapping("/select")
@Log4j2
public class SelectNodeController {
    
    @Autowired
    private ElasticsearchClient elasticsearchClient;
    
    @GetMapping("/node")
    @SneakyThrows
    public void selectNode() {
        elasticsearchClient.ping();
    }
}
