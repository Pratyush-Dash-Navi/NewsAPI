package com.navi.NewsAPI.entity;

import javax.persistence.*;

@Entity
@Table(name="api_calls")
public class Apicalls {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String endpoint;
    private String request;
    private String response;
    private int timeTaken;

    public Apicalls(String endpoint, String request, String response, int timeTaken){
        this.endpoint = endpoint;
        this.request = request;
        this.response = response;
        this.timeTaken = timeTaken;
    }

    public Apicalls(){}
    // getters and setters

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(int timeTaken) {
        this.timeTaken = timeTaken;
    }
}
