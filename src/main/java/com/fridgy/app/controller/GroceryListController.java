package com.fridgy.app.controller;

import com.fridgy.app.dto.GroceryListRequestDto;
import com.fridgy.app.dto.GroceryListResponseDto;
import com.fridgy.app.dto.ItemRequestDto;
import com.fridgy.app.dto.ItemResponseDto;
import com.fridgy.app.service.GroceryListService;
import com.fridgy.app.service.IItemService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grocerylists")
@CrossOrigin("*")
public class GroceryListController {
    @Autowired
    private GroceryListService groceryListService;

    @PostMapping
    ResponseEntity<GroceryListResponseDto> createGroceryList(HttpServletRequest request, @Valid @RequestBody GroceryListRequestDto requestDto) {
        Long userId = (Long) request.getAttribute("userId");
        GroceryListResponseDto responseDto = groceryListService.createGroceryList(userId, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PostMapping("/{groceryListId}/items")
    public ResponseEntity<ItemResponseDto> createItemForGroceryList(
            HttpServletRequest request,
            @PathVariable Long groceryListId,
            @Valid @RequestBody ItemRequestDto itemRequestDto
    ) {
        Long userId = (Long) request.getAttribute("userId");
        ItemResponseDto createdItem = groceryListService.createItemForGroceryList(userId, groceryListId, itemRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdItem);
    }

    @GetMapping("/{groceryListId}")
    ResponseEntity<GroceryListResponseDto> getGroceryList(@PathVariable Long groceryListId) {
        return ResponseEntity.ok(groceryListService.getGroceryList(groceryListId));
    }

    @GetMapping("/grocerylist/{groceryListId}/items")
    public ResponseEntity<List<ItemResponseDto>> getItemsByGroceryListId(@PathVariable Long groceryListId) {
        return ResponseEntity.ok(groceryListService.getItemsByGroceryListId(groceryListId));
    }

    @GetMapping("/users/{userId}")
    ResponseEntity<List<GroceryListResponseDto>> getAllGroceryListsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(groceryListService.getAllGroceryListsByUserId(userId));
    }

    @PutMapping("/{groceryListId}")
    ResponseEntity<GroceryListResponseDto> updateGroceryList(HttpServletRequest request, @PathVariable Long groceryListId,
                                                             @Valid @RequestBody GroceryListRequestDto requestDto) {
        Long userId = (Long) request.getAttribute("userId");
        return ResponseEntity.accepted().body(groceryListService.updateGroceryList(userId, groceryListId, requestDto));
    }

    @DeleteMapping("/{groceryListId}")
    ResponseEntity<Void> deleteGroceryList(HttpServletRequest request, @PathVariable Long groceryListId) {
        Long userId = (Long) request.getAttribute("userId");
        groceryListService.deleteGroceryList(userId, groceryListId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{groceryListId}/items/{itemId}")
    ResponseEntity<GroceryListResponseDto> addItemToGroceryList(
            HttpServletRequest request,
            @PathVariable Long groceryListId,
            @PathVariable Long itemId
    ) {
        Long userId = (Long) request.getAttribute("userId");
        GroceryListResponseDto updatedList = groceryListService.addItemToGroceryList(userId, groceryListId, itemId);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedList);
    }

    @DeleteMapping("/{groceryListId}/items/{itemId}")
    ResponseEntity<GroceryListResponseDto> removeItemFromGroceryList(
            HttpServletRequest request,
            @PathVariable Long groceryListId,
            @PathVariable Long itemId
    ) {
        Long userId = (Long) request.getAttribute("userId");
        GroceryListResponseDto updatedList = groceryListService.removeItemFromGroceryList(userId, groceryListId, itemId);
        return ResponseEntity.ok(updatedList);
    }

}
