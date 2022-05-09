package com.example.elastic.service;

import com.example.elastic.entity.Article;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author jacksparrow414
 * @date 2022/5/9
 */
@SpringBootTest
public class ArticleServiceTest {
    
    @Autowired
    private ArticleService articleService;
    
    @Test
    @SneakyThrows
    public void assertIndexArticle() {
        Article article = new Article();
        article.setCreateTime(LocalDateTime.now());
        article.setArticleContent("this is an article");
        article.setAuthor("jack");
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(article));
                String articleId = articleService.indexArticle(article);
        Assertions.assertNotNull(articleId);
    }
}