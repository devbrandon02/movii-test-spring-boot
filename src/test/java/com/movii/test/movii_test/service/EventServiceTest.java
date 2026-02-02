package com.movii.test.movii_test.service;

import com.movii.test.movii_test.client.CleverTapClient;
import com.movii.test.movii_test.dto.EventDTO;
import com.movii.test.movii_test.model.Event;
import com.movii.test.movii_test.model.User;
import com.movii.test.movii_test.repository.EventRepository;
import com.movii.test.movii_test.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CleverTapClient cleverTapClient;

    @InjectMocks
    private EventService eventService;

    private User testUser;
    private Event testEvent;
    private EventDTO testEventDTO;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setIdentity("user123");
        testUser.setName("Test User");
        testUser.setEmail("test@example.com");

        testEvent = new Event();
        testEvent.setId(1L);
        testEvent.setEventName("TestEvent");
        testEvent.setEventData("{\"key\":\"value\"}");
        testEvent.setUser(testUser);

        testEventDTO = new EventDTO();
        testEventDTO.setEventName("TestEvent");
        testEventDTO.setEventData("{\"key\":\"value\"}");
        testEventDTO.setUserId("user123");
    }

    @Test
    void createEvent_WithValidUser_ShouldCreateEventSuccessfully() {
        // Arrange
        when(userRepository.findByIdentity("user123")).thenReturn(Optional.of(testUser));
        when(eventRepository.save(any(Event.class))).thenReturn((Event) testEvent);

        // Act
        eventService.createEvent(testEventDTO);

        // Assert
        verify(userRepository).findByIdentity("user123");
        verify(eventRepository).save(any(Event.class));
        verify(cleverTapClient).uploadEvent((Event) any(Event.class));
    }

    @Test
    void createEvent_WithNonExistentUser_ShouldThrowException() {
        // Arrange
        when(userRepository.findByIdentity("nonexistent")).thenReturn(Optional.empty());
        testEventDTO.setUserId("nonexistent");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> eventService.createEvent(testEventDTO));

        assertTrue(exception.getMessage().contains("User not found"));
        verify(eventRepository, never()).save((Event) any(Event.class));
        verify(cleverTapClient, never()).uploadEvent((Event) any(Event.class));
    }

    @Test
    void getUserEvents_WithValidUser_ShouldReturnEvents() {
        // Arrange
        List<Event> events = Arrays.asList(testEvent);
        when(userRepository.findByIdentity("user123")).thenReturn(Optional.of(testUser));
        when(eventRepository.findByUserId(1L)).thenReturn(events);

        // Act
        List<EventDTO> result = eventService.getUserEvents("user123");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("TestEvent", result.get(0).getEventName());
        assertEquals("user123", result.get(0).getUserId());
        verify(userRepository).findByIdentity("user123");
        verify(eventRepository).findByUserId(1L);
    }

    @Test
    void getUserEvents_WithNonExistentUser_ShouldThrowException() {
        // Arrange
        when(userRepository.findByIdentity("nonexistent")).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> eventService.getUserEvents("nonexistent"));

        assertTrue(exception.getMessage().contains("User not found"));
        verify(eventRepository, never()).findByUserId(any());
    }

    @Test
    void getUserEvents_WithNoEvents_ShouldReturnEmptyList() {
        // Arrange
        when(userRepository.findByIdentity("user123")).thenReturn(Optional.of(testUser));
        when(eventRepository.findByUserId(1L)).thenReturn(Arrays.asList());

        // Act
        List<EventDTO> result = eventService.getUserEvents("user123");

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
