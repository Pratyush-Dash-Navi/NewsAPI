package com.navi.NewsAPI.controller;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.navi.NewsAPI.service.impl.NewsImpl;
import com.navi.NewsAPI.service.impl.UserImpl;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import static org.mockito.Mockito.*;

public class NewsAPIControllerTest {

    @InjectMocks
    private NewsImpl newsImpl;


    private UserImpl userImpl;

    @Mock
    private RestTemplate restTemplate;

    private NewsAPIController newsAPIController;

    @BeforeEach
    public void setUp() {
        newsImpl = new NewsImpl();
        userImpl = new UserImpl();
        newsAPIController = new NewsAPIController(newsImpl, userImpl);
    }

//    @Test
//    public void testNewUser_ValidUser() {
//
//        ResponseEntity<?> response = newsAPIController.newUser("abc@g.com", "general", "us", new String[]{"cnn"});
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("User created", response.getBody());
//    }


//    @Test
//    public void testGetSources_UserNotFound() {
//        when(userImpl.checkID(anyString())).thenReturn(false);
//
//        ResponseEntity<?> response = newsAPIController.getSources("123");
//
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//        assertEquals("User profile not found", response.getBody());
//    }


//    @Test
//    public void getTopHeadlines() {
//        when(userImpl.checkID(anyString())).thenReturn(false);
//
//        ResponseEntity<?> response = newsAPIController.getTopHeadlines("123");
//
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//        assertEquals("User profile not found", response.getBody());
//    }

}