package com.navi.NewsAPI.service.impl;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
@Component
public class NewsImpl {
    List<String> categories = Arrays.asList("business","entertainment","general");
    List<String> countries = Arrays.asList("us","ar","rs","hu");


    public List<String> printCategories(){
        for(String s : categories){
            System.out.println(s);
        }
        return categories;
    }
    public List<String> printCountries(){
        for(String s : countries){
            System.out.println(s);
        }
        return countries;
    }
}
