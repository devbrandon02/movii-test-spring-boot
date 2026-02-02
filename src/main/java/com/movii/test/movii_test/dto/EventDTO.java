package com.movii.test.movii_test.dto;

import jakarta.validation.constraints.NotBlank;

public class EventDTO {
    @NotBlank(message = "Event Name is required")
    private String eventName;
    private String eventData;
    @NotBlank(message = "User ID is required")
    private String userId;

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventData() {
        return eventData;
    }

    public void setEventData(String eventData) {
        this.eventData = eventData;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}