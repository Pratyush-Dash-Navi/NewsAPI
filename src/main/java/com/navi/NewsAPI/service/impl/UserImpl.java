package com.navi.NewsAPI.service.impl;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.navi.NewsAPI.entity.User;
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

    public HashMap<String, User> userMap = new HashMap<>();

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
    }
    public boolean checkPreference(String category, String country, String preference, String apiKey){
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://newsapi.org/v2/top-headlines/sources?country=" + country + "&category=" + category + "&apiKey=" + apiKey;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        String responseBody = response.getBody();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);

            String status = jsonNode.get("status").asText();
            System.out.println(status);
            int length = jsonNode.get("sources").size();
            if(length < 1){
                return false;
            }

            JsonNode sourcesNode = jsonNode.get("sources");
            if (sourcesNode != null && sourcesNode.isArray()) {
                Iterator<JsonNode> sourcesIterator = sourcesNode.elements();
                while (sourcesIterator.hasNext()) {
                    JsonNode sourceNode = sourcesIterator.next();
                    JsonNode idNode = sourceNode.get("id");
                    if (idNode != null) {
                        if(Objects.equals(preference, idNode.asText())){
                            return true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean checkPreferences(String category, String country, String[] preferences, String apiKey){
        boolean flag = true;
        for(String preference : preferences){
            if(! checkPreference(category, country,preference,apiKey)){
                flag = false;
                return false;
            }
        }
        return flag;
    }
    public String createUser(String email, String category, String country, String[] preferences){
        String id = UUID.randomUUID().toString();
        User user = new User(id,email,category,country,preferences);
        userMap.put(id, user);
        mails.put(email,true);
        return id;
    }
    public String getCategory(String id){
        return userMap.get(id).fetchCategory();
    }
    public String getCountry(String id){
        return userMap.get(id).fetchCountry();
    }

    public ResponseEntity<?> fetchHeadlines(String responseBody, String userID){
        List<String> headlines = new ArrayList<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            JsonNode articlesNode = jsonNode.get("articles");
            System.out.println(articlesNode);
            if (articlesNode.isArray()) {
                Iterator<JsonNode> sourcesIterator = articlesNode.elements();
                while (sourcesIterator.hasNext()) {
                    JsonNode articleNode = sourcesIterator.next();
                    JsonNode idNode = articleNode.get("source").get("id");
                    if (idNode != null) {
                        User tempUser = userMap.get(userID);

                        if(tempUser.preferencePresent() &&  !tempUser.matchPreference(idNode.asText())){
                        }
                        else {
                            headlines.add("source:"+ idNode.asText() + "    headline: " + articleNode.get("title").asText());
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().body(headlines);
    }
}
