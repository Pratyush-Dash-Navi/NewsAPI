package com.navi.NewsAPI.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserTests {

    private User user;

    @BeforeEach
    public void setUp() {
        String userID = "123";
        String email = "user@example.com";
        String category = "sports";
        String country = "US";
        String[] preferences = {"technology", "health"};
        user = new User(userID, email, category, country, preferences);
    }

    @Test
    public void testFetchCategory() {
        assertEquals("sports", user.fetchCategory());
    }

    @Test
    public void testFetchCountry() {
        assertEquals("US", user.fetchCountry());
    }

    @Test
    public void testMatchPreference_ExistingPreference() {
        assertTrue(user.matchPreference("technology"));
    }

    @Test
    public void testMatchPreference_NonExistingPreference() {
        assertFalse(user.matchPreference("entertainment"));
    }

    @Test
    public void testPreferencePresent_WithPreferences() {
        assertTrue(user.preferencePresent());
    }

    @Test
    public void testPreferencePresent_WithoutPreferences() {
        User userWithoutPreferences = new User("456", "user2@example.com", "health", "CA", new String[]{});
        assertFalse(userWithoutPreferences.preferencePresent());
    }
}

