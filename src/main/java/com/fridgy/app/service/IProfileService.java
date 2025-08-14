package com.fridgy.app.service;

import com.fridgy.app.dto.ProfileRequestDto;
import com.fridgy.app.dto.ProfileResponseDto;

public interface IProfileService {
    ProfileResponseDto createProfile(Long userId, ProfileRequestDto requestDto);
    ProfileResponseDto getProfileByUserId(Long userId);
    ProfileResponseDto updateProfileByUserId(Long userId, ProfileRequestDto requestDto);
}
