package com.fridgy.app.service;

import com.fridgy.app.dto.ProfileRequestDto;
import com.fridgy.app.dto.ProfileResponseDto;
import com.fridgy.app.model.Profile;
import com.fridgy.app.model.User;
import com.fridgy.app.repository.ProfileRepository;
import com.fridgy.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService implements IProfileService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProfileRepository profileRepository;

    @Override
    public ProfileResponseDto createProfile(Long userId, ProfileRequestDto requestDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        if (user.getProfile() != null) {
            throw new RuntimeException("User already has a profile.");
        }
        Profile profile = Profile.builder()
                .firstName(requestDto.getFirstName())
                .lastName(requestDto.getLastName())
                .favoriteFoods(requestDto.getFavoriteFoods())
                .build();

        profile.setUser(user); // need to set the user field by the userId you get passed in
        Profile savedProfile = profileRepository.save(profile);
        return null;
    }

    @Override
    public ProfileResponseDto getProfileByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return null;
    }

    @Override
    public ProfileResponseDto updateProfileByUserId(Long userId, ProfileRequestDto requestDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        return null;
    }

    @Override
    public void deleteProfile(Long userId) {

    }
}
