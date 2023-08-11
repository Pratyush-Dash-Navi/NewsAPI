package com.navi.NewsAPI.controller;

import com.navi.NewsAPI.entity.Apicalls;
import com.navi.NewsAPI.repository.ApiCallsRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.navi.NewsAPI.service.impl.NewsImpl;
import com.navi.NewsAPI.service.impl.UserImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import static org.mockito.Mockito.*;

//public class NewsAPIControllerTest {
//
//    @InjectMocks
//    private NewsImpl newsImpl;
//
//
//    private UserImpl userImpl;
//
//    @Mock
//    private RestTemplate restTemplate;
//
//    private NewsAPIController newsAPIController;
//
//    @BeforeEach
//    public void setUp() {
//        newsImpl = new NewsImpl();
//        userImpl = new UserImpl();
//        newsAPIController = new NewsAPIController(newsImpl, userImpl);
//    }
    import static org.mockito.Mockito.*;

    public class NewsAPIControllerTest {

        @InjectMocks
        private NewsAPIController newsController;

        @Mock
        private UserImpl userImpl;

        @Mock
        private ApiCallsRepository apiCallsRepository;

        @Before
        public void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        public void testGetTopHeadlines_UserNotFound() {
            String userId = "nonExistentUserId";

            when(userImpl.checkID(userId)).thenReturn(false);

            ResponseEntity<?> responseEntity = newsController.getTopHeadlines(userId);

            verify(apiCallsRepository, times(1)).save(any(Apicalls.class));
            assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
            assertEquals("User profile not found", responseEntity.getBody());
        }

        @Test
        public void testGetTopHeadlines_Success() {
            String userId = "validUserId";
            String country = "us";
            String category = "technology";

            when(userImpl.checkID(userId)).thenReturn(true);
            when(userImpl.getCountry(userId)).thenReturn(country);
            when(userImpl.getCategory(userId)).thenReturn(category);

            RestTemplate restTemplate = mock(RestTemplate.class);
            ResponseEntity<String> responseEntity = ResponseEntity.ok("dummyApiResponse");
            when(restTemplate.getForEntity(anyString(), eq(String.class))).thenReturn(responseEntity);

            verify(apiCallsRepository, times(1)).save(any(Apicalls.class));
            verify(userImpl, times(1)).fetchHeadlines(eq("dummyApiResponse"), eq(userId));
            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        }
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