package com.example.elastic;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;

/**
 * @author jacksparrow414
 * @date 2022/11/14
 */
public class DateTest {
    
    @Test
    public void assertDate() {
        LocalDateTime localDateTime = LocalDateTime.now();
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("US/Eastern"));
        String zoneDateString = zonedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'hh:mm:ss.SSSZ"));
        String utcDateString = zonedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'"));
        String localDateString = zonedDateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        System.out.println(zoneDateString);
        System.out.println(utcDateString);
        System.out.println(localDateString);
        
        System.out.println(localDateTime);
    }
}
