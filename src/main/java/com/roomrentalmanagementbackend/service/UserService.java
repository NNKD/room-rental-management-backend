package com.roomrentalmanagementbackend.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.roomrentalmanagementbackend.dto.ApiResponse;
import com.roomrentalmanagementbackend.dto.auth.request.AuthenticationRequest;
import com.roomrentalmanagementbackend.dto.auth.request.ForgotPasswordRequest;
import com.roomrentalmanagementbackend.dto.auth.response.AuthenticationResponse;
import com.roomrentalmanagementbackend.dto.user.request.UserRequestDTO;
import com.roomrentalmanagementbackend.dto.user.response.UserAccountResponse;
import com.roomrentalmanagementbackend.dto.user.response.UserInfoDTO;
import com.roomrentalmanagementbackend.dto.user.response.UserResponse;
import com.roomrentalmanagementbackend.entity.User;
import com.roomrentalmanagementbackend.repository.UserRepository;
import com.roomrentalmanagementbackend.utils.MessageUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
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
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    MailService mailService;
    MessageUtils messageUtils;
    @NonFinal
    @Value("${signer_key}")
    protected String SIGNER_KEY;

    public List<String> getEmailAdmin() {
        return userRepository.findEmailByRole(1);
    }

//    @Transactional
//    public AuthenticationResponse register(User user) throws JOSEException {
//        // Validate input
//        if (user.getUsername() == null || user.getUsername().isBlank()) {
//            throw new IllegalArgumentException("Username is required");
//        }
//        if (user.getPassword() == null || user.getPassword().isBlank()) {
//            throw new IllegalArgumentException("Password is required");
//        }
//        if (user.getEmail() == null || user.getEmail().isBlank()) {
//            throw new IllegalArgumentException("Email is required");
//        }
//
//        // Check if username or email exists
//        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
//            throw new IllegalArgumentException("Username already exists");
//        }
//        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
//            throw new IllegalArgumentException("Email already exists");
//        }
//
//        // Set default values if not provided
//        if (user.getFullname() == null || user.getFullname().isBlank()) {
//            user.setFullname(user.getUsername()); // Default to username if fullname is empty
//        }
//        if (user.getPhone() == null || user.getPhone().isBlank()) {
//            user.setPhone(""); // Set empty phone if not provided
//        }
//        if (user.getRole() == 0) {
//            user.setRole(0); // Default role to 0 (e.g., regular user)
//        }
//
//        // Encode password and save user
//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        User savedUser = userRepository.save(user);
//        if (savedUser == null) {
//            throw new RuntimeException("Failed to register user");
//        }
//
//        // Generate token
//        String token = generateToken(savedUser.getUsername());
//
//        return AuthenticationResponse.builder()
//                .token(token)
//                .authenticated(true)
//                .build();
//    }

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
                .orElseThrow(() -> new BadCredentialsException("userNotFound"));

        // Verify password
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("invalidPassword");
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
    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> UserResponse.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .username(user.getUsername())
                        .fullname(user.getFullname())
                        .phone(user.getPhone())
                        .role(user.getRole())
                        .totalRentalContracts(user.getRentalContracts() != null ? user.getRentalContracts().size() : 0)
                        .build())
                .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public List<UserResponse> getUsers() {
        return userRepository.findByRole(0).stream()
                .map(user -> UserResponse.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .username(user.getUsername())
                        .fullname(user.getFullname())
                        .phone(user.getPhone())
                        .role(user.getRole())
                        .totalRentalContracts(user.getRentalContracts() != null ? user.getRentalContracts().size() : 0)
                        .build())
                .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public List<UserResponse> getUsersWithRentalContracts() {
        return userRepository.findUsersWithRentalContracts().stream()
                .map(user -> UserResponse.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .username(user.getUsername())
                        .fullname(user.getFullname())
                        .phone(user.getPhone())
                        .role(user.getRole())
                        .totalRentalContracts(user.getRentalContracts() != null ? user.getRentalContracts().size() : 0)
                        .build())
                .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public List<UserResponse> getAllAdmins() {
        return userRepository.findByRole(1).stream()
                .map(user -> UserResponse.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .username(user.getUsername())
                        .fullname(user.getFullname())
                        .phone(user.getPhone())
                        .role(user.getRole())
                        .totalRentalContracts(user.getRentalContracts() != null ? user.getRentalContracts().size() : 0)
                        .build())
                .collect(Collectors.toList());
    }
    @Transactional
    public UserResponse createUser(UserRequestDTO request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("userNameExists");
        }
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("emailExists");
        }

        String randomPassword = generateRandomPassword();
        User user = User.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .fullname(request.getFullname())
                .phone(request.getPhone() != null ? request.getPhone() : "")
                .password(new BCryptPasswordEncoder().encode(randomPassword))
                .role(request.getRole())
                .build();

        User savedUser = userRepository.save(user);

        mailService.sendAccountCreationMail(
                savedUser.getEmail(),
                savedUser.getUsername(),
                savedUser.getFullname(),
                savedUser.getPhone(),
                savedUser.getRole(),
                randomPassword
        );

        return UserResponse.builder()
                .id(savedUser.getId())
                .email(savedUser.getEmail())
                .username(savedUser.getUsername())
                .fullname(savedUser.getFullname())
                .phone(savedUser.getPhone())
                .role(savedUser.getRole())
                .totalRentalContracts(0)
                .build();
    }

    @Transactional
    public UserResponse updateUser(int id, UserRequestDTO request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));

        if (!request.getEmail().equals(user.getEmail()) && userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }
        if (!request.getUsername().equals(user.getUsername()) && userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setFullname(request.getFullname());
        user.setPhone(request.getPhone() != null ? request.getPhone() : "");
        user.setRole(request.getRole());

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(new BCryptPasswordEncoder().encode(request.getPassword()));
        }

        User updatedUser = userRepository.save(user);

        return UserResponse.builder()
                .id(updatedUser.getId())
                .email(updatedUser.getEmail())
                .username(updatedUser.getUsername())
                .fullname(updatedUser.getFullname())
                .phone(updatedUser.getPhone())
                .role(updatedUser.getRole())
                .totalRentalContracts(updatedUser.getRentalContracts() != null ? updatedUser.getRentalContracts().size() : 0)
                .build();
    }

    @Transactional
    public void deleteUser(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));

        int contractCount = user.getRentalContracts() != null ? user.getRentalContracts().size() : 0;
        if (contractCount > 0) {
            throw new IllegalStateException("Cannot delete user with active rental contracts");
        }

        userRepository.deleteById(id);
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
                    .issueTime(new Date())
                    .expirationTime(new Date(Instant.now().plus(12, ChronoUnit.HOURS).toEpochMilli()))
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

    public UserInfoDTO getUserInfoAfterAuthen(String username) {
        return userRepository.findUserInfo(username)
                .orElseThrow(() -> new EntityNotFoundException(messageUtils.getMessage("user.NotFound")));
    }

    public UserAccountResponse getUserAccount(String username) {
        return userRepository.findUserAccount(username)
                .orElseThrow(() -> new EntityNotFoundException(messageUtils.getMessage("user.NotFound")));
    }

    @Transactional
    public ApiResponse updateUserAccount(String username, String newUsername) {
        User u = userRepository.findUserByUsernameIgnoreCase(username).orElse(null);
        if (u == null) {
            return ApiResponse.error(HttpStatus.NOT_FOUND, messageUtils.getMessage("user.NotFound"));
        }
        u.setUsername(newUsername);
        userRepository.save(u);
        return ApiResponse.success("Thành công");
    }

    @Transactional
    public ApiResponse updateUserPass(String username, String pass) {
        User u = userRepository.findUserByUsernameIgnoreCase(username).orElse(null);
        if (u == null) {
            return ApiResponse.error(HttpStatus.NOT_FOUND, messageUtils.getMessage("user.NotFound"));
        }
        String encodedPass = new BCryptPasswordEncoder().encode(pass);
        u.setPassword(encodedPass);
        userRepository.save(u);
        return ApiResponse.success("Thành công");
    }

    public boolean checkValidUsername(String username) {
        User u = userRepository.findUserByUsernameIgnoreCase(username).orElse(null);
        if (u == null) {
            return true;
        }
        return false;
    }

    public boolean checkPass(String username, String pass) {
        log.info("mk5: ");
        User u = userRepository.findUserByUsernameIgnoreCase(username).orElse(null);
        log.info("mk6: "+username);
        log.info("mk7: "+pass);
        if (u == null) {
            log.info("mk9: ");
            return false;
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        if (passwordEncoder.matches(pass, u.getPassword())) {
            return true;
        }
        return false;
    }
}