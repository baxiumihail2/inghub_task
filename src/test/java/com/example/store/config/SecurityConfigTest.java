package com.example.store.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

class SecurityConfigTest {

    private final SecurityConfig securityConfig = new SecurityConfig(null, null);

    @Test
    void userDetailsServiceShouldExposeConfiguredUsers() {
        PasswordEncoder passwordEncoder = securityConfig.passwordEncoder();
        UserDetailsService userDetailsService = securityConfig.userDetailsService(passwordEncoder);

        UserDetails admin = userDetailsService.loadUserByUsername("admin");
        UserDetails viewer = userDetailsService.loadUserByUsername("viewer");

        assertNotNull(admin);
        assertNotNull(viewer);
        assertTrue(passwordEncoder.matches("admin123", admin.getPassword()));
        assertTrue(passwordEncoder.matches("viewer123", viewer.getPassword()));
    }

    @Test
    void passwordEncoderShouldEncodeAndMatchPasswords() {
        PasswordEncoder passwordEncoder = securityConfig.passwordEncoder();
        String encoded = passwordEncoder.encode("secret-value");

        assertTrue(passwordEncoder.matches("secret-value", encoded));
    }
}
