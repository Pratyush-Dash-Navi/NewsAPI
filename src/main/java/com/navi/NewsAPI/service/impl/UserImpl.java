package com.navi.NewsAPI.service.impl;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class UserImpl {

    public HashMap<String, String[]> userMap = new HashMap<>();

    public HashMap<String, Boolean> mails = new HashMap<>();


    public boolean checkEmail(String email){
        if(mails.containsKey(email)){
            return false;
        }
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

    public boolean checkCategory(String category, String apiKey){
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://newsapi.org/v2/top-headlines?category=" + category + "&apiKey=" + apiKey;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            String responseBody = response.getBody();

            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(responseBody);

                String status = jsonNode.get("status").asText();
                System.out.println(status);
                if (Objects.equals(status, "ok") == true) {
                    int total = jsonNode.get("totalResults").asInt();
                    if (total > 0) {
                        return true;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    public boolean checkCountry(String country, String apiKey){
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://newsapi.org/v2/top-headlines?country=" + country + "&apiKey=" + apiKey;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
//        if (response.getStatusCode().is2xxSuccessful()) {
            String responseBody = response.getBody();
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(responseBody);

                String status = jsonNode.get("status").asText();
                System.out.println(status);
                if (Objects.equals(status, "ok") == true) {
                    int total = jsonNode.get("totalResults").asInt();
                    if (total > 0) {
                        return true;
                    }
                }
                } catch (Exception e) {
                    e.printStackTrace();
                }
        return false;
//        return true;
    }

    public String createUser(String email, String category, String country){
        String id = UUID.randomUUID().toString();
        userMap.put(id, new String[]{email, category, country});
        mails.put(email,true);
        return id;
    }
    public String getCategory(String id){
        return userMap.get(id)[1];
    }
    public String getCountry(String id){
        return userMap.get(id)[2];
    }
}
