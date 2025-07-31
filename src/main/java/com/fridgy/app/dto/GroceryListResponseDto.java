package com.fridgy.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroceryListResponseDto {
    private Long id;
    private String listName;
    private List<ItemResponseDto> items;
}
