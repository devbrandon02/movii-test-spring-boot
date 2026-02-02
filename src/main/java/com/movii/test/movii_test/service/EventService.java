package com.movii.test.movii_test.service;

import com.movii.test.movii_test.client.CleverTapClient;
import com.movii.test.movii_test.dto.EventDTO;
import com.movii.test.movii_test.model.Event;
import com.movii.test.movii_test.model.User;
import com.movii.test.movii_test.repository.EventRepository;
import com.movii.test.movii_test.repository.UserRepository;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Servicio encargado de la gestión de eventos de usuarios.
 * Permite registrar eventos localmente y sincronizarlos con CleverTap.
 */
@Service
public class EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CleverTapClient cleverTapClient;

    public EventService(EventRepository eventRepository, UserRepository userRepository,
            CleverTapClient cleverTapClient) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.cleverTapClient = cleverTapClient;
    }

    /**
     * Registra un evento para un usuario identificado por su ID interno.
     * 
     * @param userId ID del usuario.
     * @param event  Objeto de evento a registrar.
     * @return El evento guardado.
     */
    @Transactional
    public Event createEvent(Long userId, Event event) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        event.setUser(user);
        Event savedEvent = eventRepository.save(event);

        uploadToCleverTap(savedEvent);

        return savedEvent;
    }

    @Async
    protected void uploadToCleverTap(Event event) {
        cleverTapClient.uploadEvent(event);
    }

    public List<Event> getEventsByUser(Long userId) {
        return eventRepository.findByUserId(userId);
    }

    /**
     * Obtiene todos los eventos de un usuario basándose en su identidad (C.C.).
     * 
     * @param userIdentity Identidad del usuario.
     * @return Lista de eventos en formato DTO.
     */
    public List<EventDTO> getUserEvents(String userIdentity) {
        User user = userRepository.findByIdentity(userIdentity)
                .orElseThrow(() -> new IllegalArgumentException("User not found with identity: " + userIdentity));

        List<Event> events = eventRepository.findByUserId(user.getId());
        return events.stream()
                .map(event -> {
                    EventDTO dto = new EventDTO();
                    dto.setEventName(event.getEventName());
                    dto.setEventData(event.getEventData());
                    dto.setUserId(userIdentity);
                    return dto;
                })
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * Crea un evento a partir de un objeto DTO, lo guarda y lo sincroniza con
     * CleverTap.
     * 
     * @param eventDTO Datos del evento.
     */
    public void createEvent(EventDTO eventDTO) {
        User user = userRepository.findByIdentity(eventDTO.getUserId())
                .orElseThrow(
                        () -> new IllegalArgumentException("User not found with identity: " + eventDTO.getUserId()));

        Event event = new Event();
        event.setEventName(eventDTO.getEventName());
        event.setEventData(eventDTO.getEventData());
        event.setUser(user);

        Event savedEvent = eventRepository.save(event);

        uploadToCleverTap(savedEvent);
    }
}