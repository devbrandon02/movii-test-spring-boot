package com.movii.test.movii_test.service;

import com.movii.test.movii_test.client.CleverTapClient;
import com.movii.test.movii_test.dto.UserDTO;
import com.movii.test.movii_test.mapper.UserMapper;
import com.movii.test.movii_test.model.User;
import com.movii.test.movii_test.repository.UserRepository;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servicio encargado de la gestión de usuarios.
 * Proporciona lógica para la creación de usuarios, validaciones y
 * sincronización con CleverTap.
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final CleverTapClient cleverTapClient;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, CleverTapClient cleverTapClient, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.cleverTapClient = cleverTapClient;
        this.userMapper = userMapper;
    }

    /**
     * Crea un nuevo usuario a partir de un DTO, realiza validaciones y lo
     * sincroniza con CleverTap.
     * 
     * @param userDTO Los datos del usuario a crear.
     * @return El usuario creado en formato DTO.
     * @throws IllegalArgumentException si el usuario ya existe por identidad o
     *                                  email.
     */
    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);

        if (userRepository.existsByIdentity(user.getIdentity())) {
            throw new IllegalArgumentException("User with identity " + user.getIdentity() + " already exists");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("User with email " + user.getEmail() + " already exists");
        }

        User savedUser = userRepository.save(user);

        uploadToCleverTap(savedUser);

        return userMapper.toDto(savedUser);
    }

    /**
     * Crea un nuevo usuario a partir de una entidad, realiza validaciones y lo
     * sincroniza con CleverTap.
     * 
     * @param user La entidad del usuario a crear.
     * @return La entidad del usuario guardada.
     * @throws IllegalArgumentException si el usuario ya existe por identidad o
     *                                  email.
     */
    @Transactional
    public User createUser(User user) {
        if (userRepository.existsByIdentity(user.getIdentity())) {
            throw new IllegalArgumentException("User with identity " + user.getIdentity() + " already exists");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("User with email " + user.getEmail() + " already exists");
        }

        User savedUser = userRepository.save(user);

        uploadToCleverTap(savedUser);

        return savedUser;
    }

    /**
     * Sube el perfil del usuario a CleverTap de forma asíncrona.
     * 
     * @param user El usuario a sincronizar.
     */
    @Async
    protected void uploadToCleverTap(User user) {
        cleverTapClient.uploadProfile(user);
    }
}