package com.roomrentalmanagementbackend.service;

import com.roomrentalmanagementbackend.entity.User;
import com.roomrentalmanagementbackend.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;

    public List<String> getEmailAdmin() {
        return userRepository.findEmailByRole(1);
    }
}
