package com.fridgy.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileRequestDto {
    @NotBlank
    @Size(min=1, max=100)
    private String firstName;

    @NotBlank
    @Size(min=1, max=100)
    private String lastName;

    // optional favorite foods field
    @Size(max=200)
    private String favoriteFoods;
}
