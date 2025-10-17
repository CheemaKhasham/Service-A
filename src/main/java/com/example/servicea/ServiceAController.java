package com.example.servicea;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ServiceAController {

   // The URL for service-b is injected from an environment variable defined in the Kubernetes YAML
   @Value("${SERVICE_B_URL}")
   private String serviceBUrl;

   private final RestTemplate restTemplate = new RestTemplate();

   @GetMapping("/")
   public String callServiceB() {
       long startTime = System.currentTimeMillis();
       // Make a simple GET request to service-b
       String response = restTemplate.getForObject(serviceBUrl, String.class);
       long endTime = System.currentTimeMillis();
       long duration = endTime - startTime;

       return String.format("Response from Service B: [%s] | Request took %d ms", response, duration);
   }
}
