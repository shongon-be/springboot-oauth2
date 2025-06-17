package com.shongon.backend.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
@Import(UserControllerTest.TestSecurityConfig.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OAuth2User oauth2User;

    private Map<String, Object> userAttributes;

    @BeforeEach
    void setUp() {
        userAttributes = new HashMap<>();
        userAttributes.put("id", "123456");
        userAttributes.put("name", "John Doe");
        userAttributes.put("email", "john.doe@example.com");
        userAttributes.put("picture", "https://example.com/avatar.jpg");

        when(oauth2User.getName()).thenReturn("mockUserPrincipal");
    }

    @Test
    @WithMockUser
    void testGetUserInfo_Success() throws Exception {
        // Given
        when(oauth2User.getAttributes()).thenReturn(userAttributes);

        // When & Then
        mockMvc.perform(get("/user-info")
                .with(oauth2Login().oauth2User(oauth2User)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("123456"))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.picture").value("https://example.com/avatar.jpg"));
    }

    @Test
    void testGetUserInfo_WithoutAuthentication_ShouldReturn401() throws Exception {
        // When & Then
        mockMvc.perform(get("/user-info"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void testGetUserInfo_WithEmptyAttributes() throws Exception {
        // Given
        when(oauth2User.getAttributes()).thenReturn(Collections.emptyMap());

        // When & Then
        mockMvc.perform(get("/user-info")
                .with(oauth2Login().oauth2User(oauth2User)))
                .andExpect(status().isOk())  // Sửa từ andExpected thành andExpect
                .andExpect(content().json("{}"));
    }


    @EnableWebSecurity
    static class TestSecurityConfig {
        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http
                .csrf(AbstractHttpConfigurer::disable) // Tắt CSRF để kiểm thử dễ dàng hơn
                .authorizeHttpRequests(authorize -> authorize
                    .anyRequest().authenticated() // Mọi yêu cầu đều cần xác thực
                )
                // Đặt điểm vào xác thực để trả về 401 Unauthorized cho các yêu cầu chưa được xác thực
                .exceptionHandling(exception -> exception
                    .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                );
            return http.build();
        }
    }
}