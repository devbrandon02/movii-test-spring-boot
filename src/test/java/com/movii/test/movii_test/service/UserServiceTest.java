package com.movii.test.movii_test.service;

import com.movii.test.movii_test.client.CleverTapClient;
import com.movii.test.movii_test.model.User;
import com.movii.test.movii_test.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CleverTapClient cleverTapClient;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_ShouldSaveAndUpload_WhenUserIsValid() {
        User user = new User("123", "test@test.com", "Test User");
        when(userRepository.existsByIdentity(any())).thenReturn(false);
        when(userRepository.existsByEmail(any())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn((User) user);

        User created = userService.createUser(user);

        assertNotNull(created);
        assertEquals("Test User", created.getName());
        verify(userRepository, times(1)).save(user);
        verify(cleverTapClient, times(1)).uploadProfile(user);
    }

    @Test
    void createUser_ShouldThrowException_WhenIdentityExists() {
        User user = new User("123", "test@test.com", "Test User");
        when(userRepository.existsByIdentity("123")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> userService.createUser((User) user));
        verify(userRepository, never()).save(any());
        verify(cleverTapClient, never()).uploadProfile(any());
    }
}
