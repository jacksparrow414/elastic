package com.example.elastic.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * @author jacksparrow414
 * @date 2022/5/9
 *
 * PUT _index_template/article-template
 * {
 *   "index_patterns":["article-*"],
 *   "priority": 1,
 *   "template": {
 *     "settings": {
 *       "number_of_shards": 1,
 *       "number_of_replicas": 1
 *     },
 *     "mappings": {
 *       "properties": {
 *         "create_time": {
 *           "type": "date",
 *           "format": "yyyy-MM-dd'T'HH:mm:ss.SSS"
 *         },
 *         "article_content": {
 *           "type": "text"
 *         },
 *         "author": {
 *           "type": "keyword"
 *         }
 *       }
 *     }
 *   }
 * }
 */
@Data
public class Article {
    
    @JsonProperty(value = "create_time")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime createTime;
    
    @JsonProperty(value = "article_content")
    private String articleContent;
    
    @JsonProperty(value = "author")
    private String author;
}
