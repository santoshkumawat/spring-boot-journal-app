package com.santoshkumawat.journalApp.service;

import com.santoshkumawat.journalApp.api.response.WeatherResponse;
import com.santoshkumawat.journalApp.cache.AppCache;
import com.santoshkumawat.journalApp.constants.Placeholders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {

//    @Value("${weather.api.key}")
    private String apiKey;

//    private static final String API = "http://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;

    public WeatherResponse getWeather(String city){
        apiKey = appCache.appCache.get(AppCache.Keys.API_KEY.toString());
        String apiUrl = appCache.appCache.get(AppCache.Keys.WEATHER_API.toString()).replace(Placeholders.API_KEY, apiKey).replace(Placeholders.CITY, city);
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(apiUrl, HttpMethod.GET, null, WeatherResponse.class);
        return response.getBody();
    }
}
