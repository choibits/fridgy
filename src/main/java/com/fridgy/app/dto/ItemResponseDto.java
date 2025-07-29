package com.fridgy.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemResponseDto {
    private Long itemId;
    private String itemName;
    private boolean isBought;
    private int quantity;
    private String imageUrl;
    private LocalDate expirationDate;
}
