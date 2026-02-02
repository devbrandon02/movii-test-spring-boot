package com.movii.test.movii_test.mapper;

import com.movii.test.movii_test.dto.EventDTO;
import com.movii.test.movii_test.model.Event;
import com.movii.test.movii_test.model.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EventMapper {

    public Event toEntity(EventDTO dto, User user) {
        if (dto == null) {
            return null;
        }

        Event event = new Event();
        event.setEventName(dto.getEventName());
        event.setEventData(dto.getEventData());
        event.setUser(user);
        return event;
    }

    public EventDTO toDto(Event entity) {
        if (entity == null) {
            return null;
        }

        EventDTO dto = new EventDTO();
        dto.setEventName(entity.getEventName());
        dto.setEventData(entity.getEventData());
        if (entity.getUser() != null) {
            dto.setUserId(entity.getUser().getIdentity());
        }
        return dto;
    }

    public List<EventDTO> toDtoList(List<Event> entities) {
        if (entities == null) {
            return null;
        }

        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
