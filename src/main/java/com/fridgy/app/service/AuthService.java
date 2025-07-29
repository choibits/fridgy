package com.fridgy.app.service;

import com.fridgy.app.dto.AuthRequestDto;
import com.fridgy.app.dto.AuthResponseDto;
import com.fridgy.app.exception.ResourceNotFoundException;
import com.fridgy.app.model.User;
import com.fridgy.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements IAuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public AuthResponseDto signup(AuthRequestDto requestDto) {
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new ResourceNotFoundException("User", "Email", requestDto.getEmail());
        }
        String hashedPassword = passwordEncoder.encode(requestDto.getPassword());
        User user = User.builder()
                .email(requestDto.getEmail())
                .passwordHash(hashedPassword)
                .build();

        userRepository.save(user);
        return AuthResponseDto.builder()
                .success(true)
                .message("User registered successfully. Please login.")
                .build();
    }

    @Override
    public AuthResponseDto login(AuthRequestDto requestDto) {
        User user = userRepository.findByEmail(requestDto.getEmail()).orElseThrow(() -> new ResourceNotFoundException("User", "Email", requestDto.getEmail()));
        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid Email or Password");
        }
        return AuthResponseDto.builder()
                .success(true)
                .message("Login successful.")
                .id(user.getId())
                .email(user.getEmail())
                .build();
    }
}
