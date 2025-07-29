package com.fridgy.app.service;

import com.fridgy.app.dto.ItemRequestDto;
import com.fridgy.app.dto.ItemResponseDto;
import com.fridgy.app.dto.RefrigeratorRequestDto;
import com.fridgy.app.dto.RefrigeratorResponseDto;

import java.util.List;

public interface IRefrigeratorService {
    RefrigeratorResponseDto createRefrigerator(RefrigeratorRequestDto requestDto);
    RefrigeratorResponseDto getRefrigeratorById(Long id);
    List<RefrigeratorResponseDto> getAllRefrigerators(Long userId);
    RefrigeratorResponseDto updateRefrigerator(Long id, RefrigeratorRequestDto requestDto);
    RefrigeratorResponseDto addUserToFridgeByEmail(Long fridgeId, String email);
    RefrigeratorResponseDto removeUserFromFridgeByEmail(Long fridgeId, String email);
    void deleteRefrigerator(Long id);

    // ==== ITEMS ====
    List<ItemResponseDto> getItemsByFridgeId(Long fridgeId);
    ItemResponseDto addItemToFridge(Long fridgeId, ItemRequestDto itemDto);
    ItemResponseDto updateItemInFridge(Long fridgeId, Long itemId, ItemRequestDto itemDto);
    void deleteItemFromFridge(Long fridgeId, Long itemId);
}
