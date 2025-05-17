package com.example.weather.controller;

import com.example.weather.model.WeatherResponse;
import com.example.weather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping
    public WeatherResponse getWeather(@RequestParam String city,
                                      @RequestParam(defaultValue = "true") boolean celsius) {
        return weatherService.getWeather(city, celsius);
    }

    @GetMapping("/coords")
    public WeatherResponse getWeatherByCoords(@RequestParam double lat,
                                              @RequestParam double lon,
                                              @RequestParam(defaultValue = "true") boolean celsius) {
        return weatherService.getWeatherByCoordinates(lat, lon, celsius);
    }
}