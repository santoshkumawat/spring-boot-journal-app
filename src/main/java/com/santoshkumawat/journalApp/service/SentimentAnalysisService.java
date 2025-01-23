package com.santoshkumawat.journalApp.service;


import org.springframework.stereotype.Service;

@Service
public class SentimentAnalysisService {

    public String getSentiment(String text){
        return "You have no sentiment";
    }
}
