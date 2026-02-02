package com.movii.test.movii_test.controller;

import com.movii.test.movii_test.dto.EventDTO;
import com.movii.test.movii_test.service.EventService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.List;

@RestController
@RequestMapping("/events")
@Tag(name = "Events", description = "Endpoints para gestión de eventos de usuarios")
@SecurityRequirement(name = "Bearer Authentication")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    @Operation(summary = "Crear evento", description = "Registra un evento asociado a un usuario y lo sube a CleverTap")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Evento creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<String> createEvent(@Valid @RequestBody EventDTO eventDTO) {
        eventService.createEvent(eventDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Event registered successfully.");
    }

    @GetMapping("/users/{userId}")
    @Operation(summary = "Obtener eventos de usuario", description = "Retorna todos los eventos registrados para un usuario específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Eventos obtenidos exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<List<EventDTO>> getUserEvents(@PathVariable String userId) {
        List<EventDTO> events = eventService.getUserEvents(userId);
        return ResponseEntity.ok(events);
    }
}