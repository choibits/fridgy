package com.fridgy.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileResponseDto {
    // later we can add the user's id here if you wanted to
    private UUID id;
    private String firstName;
    private String lastName;
    private String favoriteFoods;
}
