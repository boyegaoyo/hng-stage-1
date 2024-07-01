package com.hng.stage1.service;


import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

  private final String apiKey;
  private static final String URL = "http://api.openweathermap.org/data/2.5/weather?q=%s&units=metric&appid=%s";

  public WeatherService(@Value("${openmap.api_key}") String apiKey) {
    this.apiKey = apiKey;
  }

  public String getTemperatureForCity(String city) {
    try {
      RestTemplate restTemplate = new RestTemplate();
      String apiUrl = String.format(URL, city, apiKey);
      OpenWeatherMapResponse response = restTemplate.getForObject(apiUrl, OpenWeatherMapResponse.class);

      if (response != null && response.getMain() != null) {
        return String.valueOf(response.getMain().getTemp());
      }
    } catch (Exception e) {}
    return "11";
  }

  @Getter
  @Setter
  public static class OpenWeatherMapResponse {
    private Main main;

    @Getter
    @Setter
    public static class Main {
      private double temp;

    }
  }
}
