package com.example.weather.controller;

import com.example.weather.model.WeatherResponse;
import com.example.weather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import java.util.LinkedList;
import java.util.List;

@Controller
public class WebController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        List<String> history = (List<String>) session.getAttribute("history");
        if (history == null) history = new LinkedList<>();
        model.addAttribute("history", history);
        model.addAttribute("selectedUnit", "celsius");
        model.addAttribute("weather", null);   // Ensure always present
        model.addAttribute("error", null);     // Ensure always present
        return "index";
    }

    @GetMapping("/weather-form")
    public String weatherForm(
            @RequestParam(required = false) String city,
            @RequestParam(required = false, defaultValue = "true") boolean celsius,
            Model model,
            HttpSession session
    ) {
        List<String> history = (List<String>) session.getAttribute("history");
        if (history == null) history = new LinkedList<>();
        model.addAttribute("history", history);

        if (city != null && !city.isEmpty()) {
            try {
                WeatherResponse weather = weatherService.getWeather(city, celsius);
                model.addAttribute("weather", weather);

                history.remove(city);
                history.add(0, city);
                if (history.size() > 5) history.remove(history.size() - 1);
                session.setAttribute("history", history);
                model.addAttribute("error", null);
            } catch (Exception e) {
                model.addAttribute("weather", null); // Always set weather
                model.addAttribute("error", "Could not fetch weather for '" + city + "'.");
            }
        } else {
            model.addAttribute("weather", null);
            model.addAttribute("error", null);
        }
        model.addAttribute("selectedUnit", celsius ? "celsius" : "fahrenheit");
        return "index";
    }

    @GetMapping("/weather-by-coords")
    public String weatherByCoords(
            @RequestParam double lat,
            @RequestParam double lon,
            @RequestParam(required = false, defaultValue = "true") boolean celsius,
            Model model,
            HttpSession session
    ) {
        List<String> history = (List<String>) session.getAttribute("history");
        if (history == null) history = new LinkedList<>();
        model.addAttribute("history", history);
        try {
            WeatherResponse weather = weatherService.getWeatherByCoordinates(lat, lon, celsius);
            model.addAttribute("weather", weather);
            model.addAttribute("error", null);
        } catch (Exception e) {
            model.addAttribute("weather", null);
            model.addAttribute("error", "Could not fetch weather for selected location.");
        }
        model.addAttribute("selectedUnit", celsius ? "celsius" : "fahrenheit");
        return "index";
    }

    @GetMapping("/aboutus")
    public String aboutUs(Model model, HttpSession session) {
        List<String> history = (List<String>) session.getAttribute("history");
        if (history == null) history = new LinkedList<>();
        model.addAttribute("history", history);
        model.addAttribute("selectedUnit", "celsius");
        model.addAttribute("weather", null);
        model.addAttribute("error", null);
        return "aboutus";
    }

    @GetMapping("/history")
    public String history(Model model, HttpSession session) {
        List<String> history = (List<String>) session.getAttribute("history");
        if (history == null) history = new LinkedList<>();
        model.addAttribute("history", history);
        model.addAttribute("selectedUnit", "celsius");
        model.addAttribute("weather", null);
        model.addAttribute("error", null);
        return "history";
    }
}