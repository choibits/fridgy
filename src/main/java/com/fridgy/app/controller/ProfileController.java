package com.fridgy.app.controller;

import com.fridgy.app.dto.ProfileRequestDto;
import com.fridgy.app.dto.ProfileResponseDto;
import com.fridgy.app.service.IProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/{userId}/profile") // all profiles are under a specific user
public class ProfileController {

    @Autowired
    private IProfileService profileService;

    @PostMapping
    public ResponseEntity<ProfileResponseDto> createProfile(
            @PathVariable Long userId,
            @RequestBody @Valid ProfileRequestDto requestDto
    ) {
        ProfileResponseDto responseDto = profileService.createProfile(userId, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<ProfileResponseDto> getProfileByUserId(
            @PathVariable Long userId
    ) {
        ProfileResponseDto responseDto = profileService.getProfileByUserId(userId);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping
    public ResponseEntity<ProfileResponseDto> updateProfileByUserId(
            @PathVariable Long userId,
            @RequestBody @Valid ProfileRequestDto requestDto
    ) {
        ProfileResponseDto responseDto = profileService.updateProfileByUserId(userId, requestDto);
        return ResponseEntity.accepted().body(responseDto);
    }
}
