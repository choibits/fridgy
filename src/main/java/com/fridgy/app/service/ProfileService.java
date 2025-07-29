package com.fridgy.app.service;

import com.fridgy.app.dto.ProfileRequestDto;
import com.fridgy.app.dto.ProfileResponseDto;
import com.fridgy.app.exception.ResourceNotFoundException;
import com.fridgy.app.model.Profile;
import com.fridgy.app.model.User;
import com.fridgy.app.repository.ProfileRepository;
import com.fridgy.app.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService implements IProfileService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProfileResponseDto createProfile(Long userId, ProfileRequestDto requestDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        if (user.getProfile() != null) {
            throw new RuntimeException("User already has a profile.");
        }

//        Profile profile = Profile.builder()
//                .firstName(requestDto.getFirstName())
//                .lastName(requestDto.getLastName())
//                .favoriteFoods(requestDto.getFavoriteFoods())
//                .build();

        Profile profile = modelMapper.map(requestDto, Profile.class);

        profile.setUser(user); // need to set the user field by the userId you get passed in
        Profile savedProfile = profileRepository.save(profile);
        return modelMapper.map(savedProfile, ProfileResponseDto.class);
    }

    @Override
    public ProfileResponseDto getProfileByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        if (user.getProfile() == null) {
            throw new RuntimeException("User does not have a profile.");
        }
        return modelMapper.map(user.getProfile(), ProfileResponseDto.class);
    }

    @Override
    public ProfileResponseDto updateProfileByUserId(Long userId, ProfileRequestDto requestDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        if (user.getProfile() == null) {
            throw new RuntimeException("User does not have a profile to update. Create a profile first.");
        }

        Profile currentProfile = user.getProfile();
        modelMapper.map(requestDto, currentProfile); // map over the current profile with the new requestDto
        Profile savedProfile = profileRepository.save(currentProfile);
        return modelMapper.map(savedProfile, ProfileResponseDto.class);
    }

}
