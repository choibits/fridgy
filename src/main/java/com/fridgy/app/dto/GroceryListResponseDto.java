package com.fridgy.app.dto;

import com.fridgy.app.model.Item;
import com.fridgy.app.model.User;
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
}
