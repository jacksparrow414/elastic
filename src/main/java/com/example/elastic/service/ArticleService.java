package com.example.elastic.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.example.elastic.entity.Article;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.http.client.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author jacksparrow414
 * @date 2022/5/9
 */
@Log4j2
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
    
    @SneakyThrows
    public List<String> simpleSearchArticle(String queryContent) {
        List<String> result = new ArrayList<>();
        Query query = QueryBuilders.bool().must(m -> m.match(mt -> mt.field("article_content").query(queryContent))).build()._toQuery();
        SearchRequest searchRequest = new SearchRequest.Builder()
            .index(INDEX_PREFIX+"*")
            .query(query)
            .source(s-> s.filter(f-> f.includes("article_content")))
            .build();
        SearchResponse<Article> search = elasticsearchClient.search(searchRequest, Article.class);
        List<Hit<Article>> hits = search.hits().hits();
        hits.forEach(item -> {
            result.add(item.id());
            log.info(item.source().getArticleContent());
        });
        return result;
    }
    
    @SneakyThrows
    public String maxDate() {
        SearchResponse<Void> search =
            elasticsearchClient.search(b -> b.index(INDEX_PREFIX + "*").size(0)
                .aggregations("max_date",
                    a -> a.max(m -> m.field("create_time").format("yyyy-MM-dd"))), Void.class);
        return search.aggregations().get("max_date").max().valueAsString();
    }
    
    @SneakyThrows
    private void separate(String maxDate) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        calendar.setTime(simpleDateFormat.parse(maxDate));
        Calendar calendar1 = Calendar.getInstance();
        int i = calendar.compareTo(calendar1);
    }
}
