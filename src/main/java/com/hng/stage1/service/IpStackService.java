package com.hng.stage1.service;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class IpStackService {

  private static final String URL = "http://api.ipstack.com/%s?access_key=%s";

  private final String apiKey;

  public IpStackService(@Value("${ipstack.api_key}") String apiKey) {
    this.apiKey = apiKey;
  }

  public String getCityFromIp(String ip) {
    try {
      RestTemplate restTemplate = new RestTemplate();
      String apiUrl = String.format(URL, ip, apiKey);
      IpStackResponse response = restTemplate.getForObject(apiUrl, IpStackResponse.class);

      if (response != null) {
        return response.getCity();
      }
    } catch (Exception e) {}
    return "New York";
  }

  @Getter
  @Setter
  @ToString
  public static class IpStackResponse {
    private String city;

    public String getCity() {
      return city != null ? city : "New York";
    }
  }
}

