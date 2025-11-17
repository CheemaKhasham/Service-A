package com.example.servicea;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ServiceAController {

    // Inject URLs for all four downstream services
    @Value("${SERVICE_B_URL}")
    private String serviceBUrl;

    @Value("${SERVICE_C_URL}")
    private String serviceCUrl;

    @Value("${SERVICE_D_URL}")
    private String serviceDUrl;

    @Value("${SERVICE_E_URL}")
    private String serviceEUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/")
    public String callAllServices() {
        long startTime = System.currentTimeMillis();
        StringBuilder responseBuilder = new StringBuilder();

        try {
            // Call Service B (The slow one)
            String responseB = restTemplate.getForObject(serviceBUrl, String.class);
            responseBuilder.append(String.format("Response from B: [%s] | ", responseB));

            // Call Service C (Fast)
            String responseC = restTemplate.getForObject(serviceCUrl, String.class);
            responseBuilder.append(String.format("Response from C: [%s] | ", responseC));

            // Call Service D (Fast)
            String responseD = restTemplate.getForObject(serviceDUrl, String.class);
            responseBuilder.append(String.format("Response from D: [%s] | ", responseD));

            // Call Service E (Fast)
            String responseE = restTemplate.getForObject(serviceEUrl, String.class);
            responseBuilder.append(String.format("Response from E: [%s] | ", responseE));

        } catch (Exception e) {
            responseBuilder.append("Error calling downstream service: " + e.getMessage());
        }

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        responseBuilder.append(String.format("Total request time: %d ms", duration));
        return responseBuilder.toString();
    }
}
