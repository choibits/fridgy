package com.fridgy.app.service;

import com.fridgy.app.dto.ItemRequestDto;
import com.fridgy.app.dto.ItemResponseDto;
import com.fridgy.app.dto.RefrigeratorRequestDto;
import com.fridgy.app.dto.RefrigeratorResponseDto;

import java.util.List;

public interface IRefrigeratorService {
    RefrigeratorResponseDto createRefrigerator(Long userId, RefrigeratorRequestDto requestDto);
    RefrigeratorResponseDto getRefrigeratorById(Long id);
    List<RefrigeratorResponseDto> getAllRefrigerators(Long userId);
    List<RefrigeratorResponseDto> getRefrigeratorsByUserId(Long userId);
    RefrigeratorResponseDto updateRefrigerator(Long userId, Long id, RefrigeratorRequestDto requestDto);
    RefrigeratorResponseDto addUserToFridgeByEmail(Long userId, Long fridgeId, String email);
    RefrigeratorResponseDto removeUserFromFridgeByEmail(Long userId, Long fridgeId, String email);
    void deleteRefrigerator(Long userId, Long id);

    // ==== ITEMS ====
    List<ItemResponseDto> getItemsByFridgeId(Long fridgeId);
    ItemResponseDto addItemToFridge(Long fridgeId, ItemRequestDto itemDto);
    ItemResponseDto updateRefrigeratorItem(Long fridgeId, Long itemId, ItemRequestDto itemRequestDto);
    void deleteItemFromFridge(Long fridgeId, Long itemId);
}
