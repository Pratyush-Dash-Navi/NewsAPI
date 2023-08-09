package com.navi.NewsAPI.entity;

import org.springframework.stereotype.Repository;

public class User {
    private String userID;
    private String email;
    private String category;
    private String country;
    private String[] preferences;

    public User(String userID, String email, String category, String country, String[] preferences){
        this.userID = userID;
        this.email = email;
        this.category = category;
        this.country = country;
        this.preferences = preferences;
    }
    public String fetchCategory(){
        return category;
    }
    public String fetchCountry(){
        return country;
    }
}
