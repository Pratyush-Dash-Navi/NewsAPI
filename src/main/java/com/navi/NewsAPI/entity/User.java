package com.navi.NewsAPI.entity;

import org.springframework.stereotype.Repository;

import java.util.HashMap;

public class User {
    private String userID;
    private String email;
    private String category;
    private String country;
    private HashMap<String,Boolean> preferences;

    public User(String userID, String email, String category, String country, String[] preferences){
        this.userID = userID;
        this.email = email;
        this.category = category;
        this.country = country;
        this.preferences = new HashMap<>();
        for(String s: preferences){
            this.preferences.put(s,true);
        }
    }
    public String fetchCategory(){
        return category;
    }
    public String fetchCountry(){
        return country;
    }
    public boolean matchPreference(String pref){
        if(preferences.containsKey(pref)){
            return true;
        }
        return false;
    }
    public boolean preferencePresent(){
        if(preferences.isEmpty()){
            return false;
        }
        return true;
    }
}
