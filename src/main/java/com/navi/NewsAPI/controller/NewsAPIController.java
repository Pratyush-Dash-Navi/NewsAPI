package com.navi.NewsAPI.controller;

import com.navi.NewsAPI.service.impl.NewsImpl;
import com.navi.NewsAPI.service.impl.UserImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@RequestMapping("/news")
@RestController
public class NewsAPIController {

    @Autowired
    private NewsImpl newsImpl;

    @Autowired
    private UserImpl userimpl;

    @GetMapping(value = "/")
    public String print(){
        return "Hello world";
    }

    @GetMapping(value = "/getCategories")
    public List<String> categories(){
        return newsImpl.printCategories();
    }
    @GetMapping(value = "/getCountries")
    public List<String> countries(){
        return newsImpl.printCountries();
    }

    @GetMapping(value = "/readCSV")
    public List<String[]> readCountries() throws IOException {
        return newsImpl.readDataFromCSV();
    }

    @PostMapping(value = "/editCSV")
    public void editCountries(@RequestParam("Country to be added") String[] country) throws IOException{
        newsImpl.editDataInCSV(country);
    }
///////////////////////////////////////////////////////////////////////
    @PostMapping(value = "/user")
    public ResponseEntity<?> newUser(
            @RequestParam("email") String email,
            @RequestParam("category") String category,
            @RequestParam("country") String country
    ){

        if(userimpl.checkEmail(email)){
            return ResponseEntity.ok().body(userimpl.createUser(email,category,country));
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    @Value("${apiKey}")
    private String apiKey; // Load your NewsAPI API key from application.properties

    @GetMapping("/top_headlines/{userId}")
    public ResponseEntity<?> getTopHeadlines(@PathVariable String userId) {

        if (! userimpl.checkID(userId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User profile not found");
        }

        String category = userimpl.getCategory(userId);
        String country = userimpl.getCountry(userId);

        RestTemplate restTemplate = new RestTemplate();
        String url = "https://newsapi.org/v2/top-headlines?country=" + country + "&category=" + category + "&apiKey=" + apiKey;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        String productsJson = response.getBody();
        System.out.println(productsJson);
        return response;




//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder()
//                .url("https://newsapi.org/v2/top-headlines?country=" + country + "&category=" + category)
//                .header("Authorization", "Bearer " + apiKey)
//                .build();
//
//        try (Response response = client.newCall(request).execute()) {
//            if (response.isSuccessful()) {
//                String responseBody = response.body().string();
//
//                ObjectMapper objectMapper = new ObjectMapper();
//                NewsApiResponse newsApiResponse = objectMapper.readValue(responseBody, NewsApiResponse.class);
//
//                return ResponseEntity.ok(newsApiResponse.getArticles());
//            } else {
//                return ResponseEntity.status(response.code()).body("Failed to fetch top headlines");
//            }
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
//        }
    }

}
