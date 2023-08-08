package com.navi.NewsAPI.controller;

import com.navi.NewsAPI.service.impl.NewsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RequestMapping("/news")
@RestController
public class NewsAPIController {

    @Autowired
    private NewsImpl newsImpl;
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

}
