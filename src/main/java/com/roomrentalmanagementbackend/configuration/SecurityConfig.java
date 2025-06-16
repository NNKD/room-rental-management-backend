package com.roomrentalmanagementbackend.configuration;

import com.roomrentalmanagementbackend.filter.JwtAuthenticationFilter;
import com.roomrentalmanagementbackend.service.UserService;
import com.roomrentalmanagementbackend.utils.JWTUtils;
import com.roomrentalmanagementbackend.utils.MessageUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SecurityConfig {
    JWTUtils jwtUtils;
    UserService userService;
    MessageUtils messageUtils;
    List<String> securedPaths = Arrays.asList("/dashboard/**", "/dashboard-user/**","/cloudinary/**", "/auth/all-users", "/apartments/available");

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> {})
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(securedPaths.toArray(new String[0])).authenticated()
                        .anyRequest().permitAll()
                ).addFilterBefore(new JwtAuthenticationFilter(jwtUtils, userService, securedPaths, messageUtils), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


}