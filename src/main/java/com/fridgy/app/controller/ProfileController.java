package com.fridgy.app.controller;

import com.fridgy.app.dto.ProfileRequestDto;
import com.fridgy.app.dto.ProfileResponseDto;
import com.fridgy.app.service.IProfileService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/{userId}/profile")
// all profiles are under a specific user so that's why we have a fixed endpoint with a path variable
// however, now we are using the user id from the token
@CrossOrigin("*")
public class ProfileController {

    @Autowired
    private IProfileService profileService;

    @PostMapping
    public ResponseEntity<ProfileResponseDto> createProfile(
            HttpServletRequest request,
            @RequestBody @Valid ProfileRequestDto requestDto
    ) {
        Long userId = (Long) request.getAttribute("userId");
        ProfileResponseDto responseDto = profileService.createProfile(userId, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<ProfileResponseDto> getProfileByUserId(
            HttpServletRequest request
    ) {
        Long userId = (Long) request.getAttribute("userId");
        ProfileResponseDto responseDto = profileService.getProfileByUserId(userId);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping
    public ResponseEntity<ProfileResponseDto> updateProfileByUserId(
            HttpServletRequest request,
            @RequestBody @Valid ProfileRequestDto requestDto
    ) {
        Long userId = (Long) request.getAttribute("userId");
        ProfileResponseDto responseDto = profileService.updateProfileByUserId(userId, requestDto);
        return ResponseEntity.accepted().body(responseDto);
    }
}
