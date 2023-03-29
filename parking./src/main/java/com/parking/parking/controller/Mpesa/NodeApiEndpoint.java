package com.parking.parking.controller.Mpesa;

import org.springframework.web.client.RestTemplate;

public class NodeApiEndpoint {
    private String url;
    public NodeApiEndpoint(String url) {
        this.url = url;
    }
    public String getData() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, String.class);
    }
}
