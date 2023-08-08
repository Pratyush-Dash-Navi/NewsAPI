package com.navi.NewsAPI.service.impl;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
@Component
public class NewsImpl {
    List<String> categories = Arrays.asList("business","entertainment","general");
    List<String> countries = Arrays.asList("us","ar","rs","hu");

    private static final String FILE_PATH = "/Users/pratyushdash/Downloads/NewsAPI/src/main/resources/lists.csv";
    public static List<String[]> readDataFromCSV() throws IOException {
        List<String[]> data = new ArrayList<>();

        try (CSVReader reader = new CSVReaderBuilder(new FileReader(FILE_PATH)).build()) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                data.add(line);
            }
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }

        return data;
    }

    // Method to write data to CSV file
    public static void editDataInCSV(String[] data) throws IOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(FILE_PATH, true))) {
            writer.writeAll(Collections.singleton(data));
        }
    }

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
