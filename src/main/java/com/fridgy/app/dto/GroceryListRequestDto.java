package com.fridgy.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroceryListRequestDto {

    // Removed userId as an input because now you get it from the JWT token
    //    @NotNull
    //    private Long userId;

    @NotBlank
    @Size(min=1, max=100)
    private String listName;
}
