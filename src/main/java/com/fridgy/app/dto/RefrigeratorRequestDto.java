package com.fridgy.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefrigeratorRequestDto {
    // allows multiple users to be added to a fridge
    private List<Long> userIds;

    @NotBlank
    @Size(min = 1, max = 100)
    private String fridgeName;
}
