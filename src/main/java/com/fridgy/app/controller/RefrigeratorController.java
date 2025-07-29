package com.fridgy.app.controller;

import com.fridgy.app.dto.ItemRequestDto;
import com.fridgy.app.dto.ItemResponseDto;
import com.fridgy.app.dto.RefrigeratorRequestDto;
import com.fridgy.app.dto.RefrigeratorResponseDto;
import com.fridgy.app.service.IItemService;
import com.fridgy.app.service.IRefrigeratorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/refrigerators")
public class RefrigeratorController {

    @Autowired
    private IRefrigeratorService refrigeratorService;

    @Autowired
    private IItemService itemService;

    @PostMapping
    public RefrigeratorResponseDto createRefrigerator(@Valid @RequestBody RefrigeratorRequestDto requestDto) {
        return refrigeratorService.createRefrigerator(requestDto);
    }

    @GetMapping("/{id}")
    public RefrigeratorResponseDto getRefrigeratorById(@PathVariable Long id) {
        return refrigeratorService.getRefrigeratorById(id);
    }

    @GetMapping("/user/{userId}")
    public List<RefrigeratorResponseDto> getAllRefrigeratorsForUser(
            @PathVariable Long userId) {
        return refrigeratorService.getAllRefrigerators(userId);
    }

    @PutMapping("/{id}")
    public RefrigeratorResponseDto updateRefrigerator(
            @PathVariable Long id,
            @Valid @RequestBody RefrigeratorRequestDto requestDto) {
        return refrigeratorService.updateRefrigerator(id, requestDto);
    }

    @PostMapping("/{id}/add-user")
    public ResponseEntity<RefrigeratorResponseDto> addUserToFridgeByEmail(
            @PathVariable Long id,
            @RequestParam String email) {
        RefrigeratorResponseDto responseDto = refrigeratorService.addUserToFridgeByEmail(id, email);
        return ResponseEntity.ok().body(responseDto);
    }

    @DeleteMapping("/{id}/remove-user")
    public ResponseEntity<RefrigeratorResponseDto> removeUserFromFridgeByEmail(
            @PathVariable Long id,
            @RequestParam String email) {
        RefrigeratorResponseDto responseDto = refrigeratorService.removeUserFromFridgeByEmail(id, email);
        return ResponseEntity.ok().body(responseDto);
    }

    @DeleteMapping("/{id}")
    public void deleteRefrigerator(@PathVariable Long id) {
        refrigeratorService.deleteRefrigerator(id);
    }

    // ==== ITEMS ====
    @GetMapping
    public ResponseEntity<List<ItemResponseDto>> getItemsInFridge(@PathVariable Long fridgeId) {
        return ResponseEntity.ok(itemService.getItems(fridgeId));
    }

    @PostMapping
    public ResponseEntity<ItemResponseDto> addItemToFridge(@PathVariable Long fridgeId,
                                                           @Valid @RequestBody ItemRequestDto itemDto) {
        return ResponseEntity.ok(itemService.addItemToFridge(fridgeId, itemDto));
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<ItemResponseDto> updateItemInFridge(@PathVariable Long fridgeId,
                                                              @PathVariable Long itemId,
                                                              @Valid @RequestBody ItemRequestDto itemDto) {
        return ResponseEntity.ok(itemService.updateItemInFridge(fridgeId, itemId, itemDto));
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteItemFromFridge(@PathVariable Long fridgeId,
                                                     @PathVariable Long itemId) {
        itemService.deleteItemFromFridge(fridgeId, itemId);
        return ResponseEntity.noContent().build();
    }
}