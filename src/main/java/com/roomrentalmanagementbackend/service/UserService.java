package com.roomrentalmanagementbackend.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.roomrentalmanagementbackend.dto.auth.request.AuthenticationRequest;
import com.roomrentalmanagementbackend.dto.auth.request.ForgotPasswordRequest;
import com.roomrentalmanagementbackend.dto.auth.response.AuthenticationResponse;
import com.roomrentalmanagementbackend.entity.User;
import com.roomrentalmanagementbackend.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    MailService mailService;
    @NonFinal
    protected static final String SIGNER_KEY = "61vYixZpRImO5DL5Nugi7jHOuCaJ2W2P0mHlPjfhfNAnUOBm+jOOJsPfjhgcbkWd";

    public List<String> getEmailAdmin() {
        return userRepository.findEmailByRole(1);
    }

    @Transactional
    public AuthenticationResponse register(User user) throws JOSEException {
        // Validate input
        if (user.getUsername() == null || user.getUsername().isBlank()) {
            throw new IllegalArgumentException("Username is required");
        }
        if (user.getPassword() == null || user.getPassword().isBlank()) {
            throw new IllegalArgumentException("Password is required");
        }
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email is required");
        }

        // Check if username or email exists
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        // Set default values if not provided
        if (user.getFullname() == null || user.getFullname().isBlank()) {
            user.setFullname(user.getUsername()); // Default to username if fullname is empty
        }
        if (user.getPhone() == null || user.getPhone().isBlank()) {
            user.setPhone(""); // Set empty phone if not provided
        }
        if (user.getRole() == 0) {
            user.setRole(0); // Default role to 0 (e.g., regular user)
        }

        // Encode password and save user
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        if (savedUser == null) {
            throw new RuntimeException("Failed to register user");
        }

        // Generate token
        String token = generateToken(savedUser.getUsername());

        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    @Transactional
    public AuthenticationResponse login(AuthenticationRequest request) throws JOSEException {
        // Validate input
        if (request.getUsername() == null || request.getUsername().isBlank()) {
            throw new IllegalArgumentException("Username is required");
        }
        if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw new IllegalArgumentException("Password is required");
        }

        User user = (User) userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BadCredentialsException("User not found"));

        // Verify password
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        // Generate token
        String token = generateToken(request.getUsername());

        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }
    @Transactional
    public void forgotPassword(ForgotPasswordRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Email không tồn tại"));
        String newPassword = generateRandomPassword();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        mailService.sendPasswordResetMail(user.getEmail(), newPassword);
    }

    private String generateRandomPassword() {
        // Tạo mật khẩu ngẫu nhiên, ví dụ 8 ký tự
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
            password.append(characters.charAt(random.nextInt(characters.length())));
        }
        return password.toString();
    }
    private String generateToken(String username) throws JOSEException {
        try {
            JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
            JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                    .subject(username)
                    .issuer("devteria.com")
                    .issueTime(new Date())
                    .expirationTime(new Date(Instant.now().plus(15, ChronoUnit.MINUTES).toEpochMilli()))
                    .claim("customClaim", "Custom")
                    .build();
            Payload payload = new Payload(jwtClaimsSet.toJSONObject());
            JWSObject jwsObject = new JWSObject(header, payload);
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException("Failed to generate token");
        }
    }
}