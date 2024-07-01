package com.hng.stage1.controller;

import com.hng.stage1.service.IpStackService;
import com.hng.stage1.service.WeatherService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class WeatherController {

  private final IpStackService stackService;
  private final WeatherService weatherService;

  @GetMapping("/hello")
  public HelloResponse hello(@RequestParam("visitor_name") String visitorName, HttpServletRequest request) {

    String clientIp = request.getRemoteAddr();
    String location = stackService.getCityFromIp(clientIp);
    String temperature = weatherService.getTemperatureForCity(location);
    String greeting = String.format("Hello, %s!, the temperature is %s degrees Celsius in %s", visitorName, temperature, location);

    HelloResponse helloResponse = new HelloResponse();
    helloResponse.setClient_ip(clientIp);
    helloResponse.setLocation(location);
    helloResponse.setGreeting(greeting);
    return helloResponse;
  }

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class HelloResponse {
    private String client_ip;
    private String location;
    private String greeting;
  }
}
