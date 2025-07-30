package com.fridgy.app.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemRequestDto {
    // TODO: Not sure if I need these ids
    private Long groceryListId;

    private Long refrigeratorId;

    @NotBlank
    @Size(min = 1, max = 50)
    private String itemName;

    @NotNull
    @Size(min = 1)
    private int quantity;

    @NotNull(message = "Expiration date is required")
    @FutureOrPresent(message = "Expiration date must be in the present or future")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expirationDate;

}
