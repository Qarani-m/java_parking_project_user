package com.parking.parking.controller.Sms;

import org.springframework.web.client.RestTemplate;

public class ApiClient {
    public static void main(String[] args) {
        ApiClient apiClient = new ApiClient();
        SmsResponse smsResponse = apiClient.get("sendSms?phoneNumber=123456&message=Hello", SmsResponse.class);
    }
    private static final String BASE_URL = "http://localhost:6068/";

    private RestTemplate restTemplate;

    public ApiClient() {
        this.restTemplate = new RestTemplate();
    }

    public <T> T get(String path, Class<T> responseType) {
        String url = BASE_URL + path;
        return restTemplate.getForObject(url, responseType);
    }

    public <T> T post(String path, Object request, Class<T> responseType) {
        String url = BASE_URL + path;
        return restTemplate.postForObject(url, request, responseType);
    }
}
