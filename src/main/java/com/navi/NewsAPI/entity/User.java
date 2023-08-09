package com.navi.NewsAPI.entity;

import org.springframework.stereotype.Repository;

public class User {
    private String userID;
    private String email;
    private String category;
    private String country;

    public User(String userID, String email, String category, String country){
        this.userID = userID;
        this.email = email;
        this.category = category;
        this.country = country;
    }
    public String fetchCategory(){
        return category;
    }
    public String fetchCountry(){
        return country;
    }
}
