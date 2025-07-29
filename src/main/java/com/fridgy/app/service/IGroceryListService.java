package com.fridgy.app.service;

import com.fridgy.app.dto.GroceryListRequestDto;
import com.fridgy.app.dto.GroceryListResponseDto;

import java.util.List;

public interface IGroceryListService {
    GroceryListResponseDto createGroceryList(GroceryListRequestDto requestDto);
    GroceryListResponseDto getGroceryList(Long groceryListId);
    List<GroceryListResponseDto> getAllGroceryListsByUserId(Long userId);
    GroceryListResponseDto updateGroceryList(Long groceryListId, GroceryListRequestDto requestDto);
    void deleteGroceryList(Long groceryListId);
}
