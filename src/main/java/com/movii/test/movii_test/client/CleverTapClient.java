package com.movii.test.movii_test.client;

import com.movii.test.movii_test.model.User;
import com.movii.test.movii_test.model.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CleverTapClient {

    private static final Logger logger = LoggerFactory.getLogger(CleverTapClient.class);

    @Value("${clevertap.account.id}")
    private String accountId;

    @Value("${clevertap.passcode}")
    private String passcode;

    @Value("${clevertap.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    public CleverTapClient() {
        this.restTemplate = new RestTemplate();
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-CleverTap-Account-Id", accountId);
        headers.set("X-CleverTap-Passcode", passcode);
        return headers;
    }

    public void uploadProfile(User user) {
        String url = apiUrl + "/upload";

        Map<String, Object> profileData = new HashMap<>();
        profileData.put("identity", user.getIdentity());
        profileData.put("Email", user.getEmail());
        profileData.put("Name", user.getName());
        profileData.put("ts", System.currentTimeMillis() / 1000);
        profileData.put("type", "profile");

        Map<String, Object> payload = new HashMap<>();
        payload.put("d", List.of(profileData));

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, getHeaders());

        try {
            restTemplate.postForEntity(url, entity, String.class);
            logger.info("Successfully uploaded profile to CleverTap for user: {}", user.getIdentity());
        } catch (HttpStatusCodeException e) {
            logger.error("Failed to upload profile to CleverTap: {} {}. Response body: {}",
                    e.getStatusCode(), e.getStatusText(), e.getResponseBodyAsString());
        } catch (Exception e) {
            logger.error("Unexpected error uploading profile to CleverTap: {}", e.getMessage());
        }
    }

    public void uploadEvent(Event event) {
        String url = apiUrl + "/upload";

        Map<String, Object> eventData = new HashMap<>();
        eventData.put("identity", event.getUser().getIdentity());
        eventData.put("evtName", event.getEventName());
        eventData.put("ts", System.currentTimeMillis() / 1000);
        eventData.put("type", "event");
        eventData.put("evtData", event.getEventData());

        Map<String, Object> payload = new HashMap<>();
        payload.put("d", List.of(eventData));

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, getHeaders());

        try {
            restTemplate.postForEntity(url, entity, String.class);
            logger.info("Successfully uploaded event to CleverTap for user: {}", event.getUser().getIdentity());
        } catch (HttpStatusCodeException e) {
            logger.error("Failed to upload event to CleverTap: {} {}. Response body: {}",
                    e.getStatusCode(), e.getStatusText(), e.getResponseBodyAsString());
        } catch (Exception e) {
            logger.error("Unexpected error uploading event to CleverTap: {}", e.getMessage());
        }
    }
}
