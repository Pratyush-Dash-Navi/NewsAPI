package com.navi.NewsAPI.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name="news_user")
public class User {
    @Id
    private String userId;
    private String email;
    private String category;
    private String country;
    private String[] preferenceList;

    public User(String userID, String email, String category, String country, String[] preferences){
        this.userId = userID;
        this.email = email;
        this.category = category;
        this.country = country;
        this.preferenceList = preferences;
    }
    public User() {    }

    public String fetchCategory(){
        return category;
    }
    public String fetchCountry(){
        return country;
    }
    public boolean matchPreference(String pref){
        if((preferenceList.length != 0)){
            for(String p: preferenceList){
                if(Objects.equals(p, pref)){
                    return true;
                }
            }
        }
        return false;
    }
    public boolean preferencePresent(){
        if(preferenceList.length == 0){
            return false;
        }
        return true;
    }
}
