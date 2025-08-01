package com.fridgy.app.service;

import com.fridgy.app.dto.ItemRequestDto;
import com.fridgy.app.dto.ItemResponseDto;

import java.util.List;

public interface IItemService {
    ItemResponseDto createItem(ItemRequestDto dto);

    ItemResponseDto getItemById(Long itemId);

    List<ItemResponseDto> getAllItems();

    ItemResponseDto updateItemById(Long itemId, ItemRequestDto itemRequestDto);

//    ItemResponseDto addItemToGroceryList(Long itemId, Long groceryListId);
//
//    ItemResponseDto removeItemFromGroceryList(Long itemId, Long groceryListId);
//
    List<ItemResponseDto> moveItemsToFridge(List<Long> itemIds, Long fridgeId);

    void deleteItem(Long itemId);
}
