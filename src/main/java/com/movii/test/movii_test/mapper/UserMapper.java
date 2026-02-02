package com.movii.test.movii_test.mapper;

import com.movii.test.movii_test.dto.UserDTO;
import com.movii.test.movii_test.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserDTO dto) {
        if (dto == null) {
            return null;
        }

        User user = new User();
        user.setIdentity(dto.getIdentity());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        return user;
    }

    public UserDTO toDto(User entity) {
        if (entity == null) {
            return null;
        }

        UserDTO dto = new UserDTO();
        dto.setIdentity(entity.getIdentity());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        return dto;
    }
}
