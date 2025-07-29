package com.fridgy.app.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemResponseDto {
    private Long itemId;
    private String itemName;
    private boolean isBought;
    private int quantity;
    private String imageUrl;
    private LocalDate expirationDate;
}
