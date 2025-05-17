package com.example.weather.service;

import com.example.weather.model.WeatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

    public WeatherResponse getWeather(String city, boolean isCelsius) {
        String units = isCelsius ? "metric" : "imperial";
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city +
                "&appid=" + apiKey + "&units=" + units;
        return getWeatherFromUrl(url, isCelsius);
    }

    public WeatherResponse getWeatherByCoordinates(double lat, double lon, boolean isCelsius) {
        String units = isCelsius ? "metric" : "imperial";
        String url = "https://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon +
                "&appid=" + apiKey + "&units=" + units;
        return getWeatherFromUrl(url, isCelsius);
    }

    private WeatherResponse getWeatherFromUrl(String url, boolean isCelsius) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            String result = restTemplate.getForObject(url, String.class);
            JSONObject json = new JSONObject(result);

            if (json.has("main") && json.has("weather")) {
                JSONObject weatherObj = json.getJSONArray("weather").getJSONObject(0);
                String description = weatherObj.getString("description");
                String icon = weatherObj.getString("icon");
                String iconUrl = "https://openweathermap.org/img/wn/" + icon + "@2x.png";

                double temperature = json.getJSONObject("main").getDouble("temp");
                int humidity = json.getJSONObject("main").getInt("humidity");
                double windSpeed = json.getJSONObject("wind").getDouble("speed");
                int pressure = json.getJSONObject("main").getInt("pressure");
                String cityName = json.has("name") ? json.getString("name") : "";
                double lat = json.getJSONObject("coord").getDouble("lat");
                double lon = json.getJSONObject("coord").getDouble("lon");

                return new WeatherResponse(cityName, description, temperature, humidity, iconUrl, windSpeed, pressure, isCelsius, lat, lon);
            } else if (json.has("message")) {
                throw new RuntimeException("Error from API: " + json.getString("message"));
            } else {
                throw new RuntimeException("Unexpected response from API.");
            }
        } catch (RestClientException e) {
            throw new RuntimeException("API request failed: " + e.getMessage());
        }
    }
}