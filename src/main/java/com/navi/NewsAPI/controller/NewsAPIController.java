package com.navi.NewsAPI.controller;

import com.navi.NewsAPI.entity.Apicalls;
import com.navi.NewsAPI.entity.User;
import com.navi.NewsAPI.repository.ApiCallsRepository;
import com.navi.NewsAPI.repository.UserRepository;
import com.navi.NewsAPI.service.impl.NewsImpl;
import com.navi.NewsAPI.service.impl.UserImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@RequestMapping("/news")
@RestController
public class NewsAPIController {

    private final NewsImpl newsImpl;

    private final UserImpl userimpl;

    @Autowired
    private ApiCallsRepository apiCallsRepository;
    @Autowired
    public NewsAPIController(NewsImpl newsImpl, UserImpl userimpl) {
        this.newsImpl = newsImpl;
        this.userimpl = userimpl;

    }

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
    @Value("${apiKey}")
    private String apiKey; // Load API key from application.properties

    @PostMapping(value = "/user")
    public ResponseEntity<?> newUser(
            @RequestParam("email") String email,
            @RequestParam("category") String category,
            @RequestParam("country") String country,
            @RequestParam("preferences") String[] preferences
    ){
        if(userimpl.checkEmail(email)){
            if(userimpl.checkCategory(category,apiKey)){
                if(userimpl.checkCountry(country,apiKey)){
                    if(userimpl.checkPreferences(category,country,preferences,apiKey)){
                        return ResponseEntity.ok().body(userimpl.createUser(email,category,country,preferences));
                    }
                    else{
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid preferences");
                    }
                }
                else{
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid country");
                }
            }
            else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid category");
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid email");
    }

    @GetMapping("/sources/{userId}")
    public ResponseEntity<?> getSources(@PathVariable String userId) {

        if (!userimpl.checkID(userId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User profile not found");
        }
        String category = userimpl.getCategory(userId);
        String country = userimpl.getCountry(userId);
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://newsapi.org/v2/top-headlines/sources?country=" + country + "&category=" + category + "&apiKey=" + apiKey;
        ResponseEntity<String> responses = restTemplate.getForEntity(url, String.class);
        String productsJson = responses.getBody();
        System.out.println(productsJson);
        return responses;
    }
    @GetMapping("/top-headlines/{userId}")
    public ResponseEntity<?> getTopHeadlines(@PathVariable String userId) {

        if (!userimpl.checkID(userId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User profile not found");
        }
        String category = userimpl.getCategory(userId);
        String country = userimpl.getCountry(userId);

        long startTime = System.currentTimeMillis();

        RestTemplate restTemplate = new RestTemplate();
        String url = "https://newsapi.org/v2/top-headlines?country=" + country + "&category=" + category + "&apiKey=" + apiKey;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        String productsJson = response.getBody();

        long endTime = System.currentTimeMillis();
        int timeTaken = (int) (endTime - startTime);
        String endpoint = "/top-headlines/{userId}";
        String request = url;
        String responses = productsJson;
        Apicalls apicalls = new Apicalls(endpoint, request, responses, timeTaken);
        apiCallsRepository.save(apicalls);


        System.out.println(productsJson);
        return userimpl.fetchHeadlines(productsJson,userId);
    }
}
