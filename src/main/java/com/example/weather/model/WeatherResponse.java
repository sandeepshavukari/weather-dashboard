package com.example.weather.model;

public class WeatherResponse {
    private String city;
    private String description;
    private double temperature;
    private int humidity;
    private String iconUrl;
    private double windSpeed;
    private int pressure;
    private boolean isCelsius; // for UI toggle
    private double lat;
    private double lon;

    public WeatherResponse() {}

    public WeatherResponse(String city, String description, double temperature, int humidity,
                          String iconUrl, double windSpeed, int pressure, boolean isCelsius, double lat, double lon) {
        this.city = city;
        this.description = description;
        this.temperature = temperature;
        this.humidity = humidity;
        this.iconUrl = iconUrl;
        this.windSpeed = windSpeed;
        this.pressure = pressure;
        this.isCelsius = isCelsius;
        this.lat = lat;
        this.lon = lon;
    }

    // Getters and setters
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getTemperature() { return temperature; }
    public void setTemperature(double temperature) { this.temperature = temperature; }

    public int getHumidity() { return humidity; }
    public void setHumidity(int humidity) { this.humidity = humidity; }

    public String getIconUrl() { return iconUrl; }
    public void setIconUrl(String iconUrl) { this.iconUrl = iconUrl; }

    public double getWindSpeed() { return windSpeed; }
    public void setWindSpeed(double windSpeed) { this.windSpeed = windSpeed; }

    public int getPressure() { return pressure; }
    public void setPressure(int pressure) { this.pressure = pressure; }

    public boolean isCelsius() { return isCelsius; }
    public void setCelsius(boolean celsius) { isCelsius = celsius; }

    public double getLat() { return lat; }
    public void setLat(double lat) { this.lat = lat; }

    public double getLon() { return lon; }
    public void setLon(double lon) { this.lon = lon; }
}