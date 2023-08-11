package com.navi.NewsAPI.service.impl;

import com.navi.NewsAPI.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserImplTest {

    @InjectMocks
    private UserImpl userImpl;


    @BeforeEach
    public void setUp() {
        userImpl = new UserImpl();
    }

    @Test
    public void testFetchHeadlines() {
        String responseBody = "{\"articles\":[{\"source\":{\"id\":\"cnn\"},\"title\":\"Headline\"}," +
                "{\"source\":{\"id\":\"abc-news\"},\"title\":\"Headline\"}]}";
        String userID = "123";

        userImpl.userMap.put(userID, new User(userID, "user@example.com", "business", "us", any()));

        ResponseEntity<?> response = userImpl.fetchHeadlines(responseBody, userID);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof java.util.List);
        java.util.List<?> headlines = (java.util.List<?>) response.getBody();
        assertEquals(1, headlines.size());
        assertEquals("source:cnn    headline: Headline", headlines.get(0));
    }
}