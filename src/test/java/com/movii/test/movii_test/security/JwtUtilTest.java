package com.movii.test.movii_test.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        // Use a dummy secret key (base64 encoded 256-bit key) and 1 hour expiration
        String secret = "c29tZV9zdXBlcl9zZWNyZXRfa2V5X3RoYXRfaXNfbG9uZ19lbm91Z2hfZm9yX2hzMjU2";
        jwtUtil = new JwtUtil(secret, 3600000);
    }

    @Test
    void generateToken_ShouldReturnValidToken() {
        String token = jwtUtil.generateToken("testuser");
        assertNotNull(token);
        assertTrue(jwtUtil.validateToken(token, "testuser"));
    }

    @Test
    void extractUsername_ShouldReturnCorrectUsername() {
        String token = jwtUtil.generateToken("testuser");
        String username = jwtUtil.extractUsername(token);
        assertEquals("testuser", username);
    }

    @Test
    void validateToken_ShouldReturnFalse_WhenUsernameDoesNotMatch() {
        String token = jwtUtil.generateToken("testuser");
        assertFalse(jwtUtil.validateToken(token, "otheruser"));
    }
}
