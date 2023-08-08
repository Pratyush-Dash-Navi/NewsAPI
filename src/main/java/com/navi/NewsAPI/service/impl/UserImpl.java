package com.navi.NewsAPI.service.impl;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;
import org.springframework.stereotype.Component;

@Component
public class UserImpl {

    public HashMap<String, String[]> userMap = new HashMap<>();
    public boolean checkEmail(String email){
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
    public boolean checkID(String id){
        return userMap.containsKey(id);
    }

    public String createUser(String email, String category, String country){
        String id = UUID.randomUUID().toString();
        userMap.put(id, new String[]{email, category, country});
        return id;
    }
    public String getCategory(String id){
        return userMap.get(id)[1];
    }
    public String getCountry(String id){
        return userMap.get(id)[2];
    }
}
