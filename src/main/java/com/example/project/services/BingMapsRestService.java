package com.example.project.services;

import com.example.project.dtos.bingMapsResponse.BingMapsResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BingMapsRestService {
    private final RestTemplate restTemplate;

    @Value("${config.bing.key}")
    private String key;

    public BingMapsRestService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public BingMapsResponse getRouteDetails(String wayPoint1, String wayPoint2) {
        String url = String.format("http://dev.virtualearth.net/REST/v1/Routes?wayPoint.1=%s&wayPoint.2=%s&key=%s",
                wayPoint1, wayPoint2, key);
        return this.restTemplate.getForObject(url, BingMapsResponse.class);
    }
}
