package com.fridgy.app.service;

import com.fridgy.app.dto.GroceryListRequestDto;
import com.fridgy.app.dto.GroceryListResponseDto;
import com.fridgy.app.dto.ItemResponseDto;

import java.util.List;

public interface IGroceryListService {
    GroceryListResponseDto createGroceryList(Long userId, GroceryListRequestDto requestDto);
    GroceryListResponseDto getGroceryList(Long groceryListId);
    List<GroceryListResponseDto> getAllGroceryListsByUserId(Long userId);
    List<ItemResponseDto> getItemsByGroceryListId(Long groceryListId);
    GroceryListResponseDto updateGroceryList(Long userId, Long groceryListId, GroceryListRequestDto requestDto);
    GroceryListResponseDto addItemToGroceryList(Long userId, Long groceryListId, Long itemId);
    GroceryListResponseDto removeItemFromGroceryList(Long userId, Long groceryListId, Long itemId);
    void deleteGroceryList(Long userId, Long groceryListId);

}
