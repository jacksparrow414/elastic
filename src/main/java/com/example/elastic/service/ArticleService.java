package com.example.elastic.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import com.example.elastic.entity.Article;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author jacksparrow414
 * @date 2022/5/9
 */
@Service
public class ArticleService {
    
    private static final String INDEX_PREFIX = "article-";
    
    @Autowired
    private ElasticsearchClient elasticsearchClient;
    
    @SneakyThrows
    public String indexArticle(Article article) {
        int indexName = article.getCreateTime().getYear();
        IndexRequest<Article> indexRequest = new IndexRequest.Builder<Article>()
            .index(INDEX_PREFIX + indexName).document(article).build();
        IndexResponse index = elasticsearchClient.index(indexRequest);
        return index.id();
    }
}
