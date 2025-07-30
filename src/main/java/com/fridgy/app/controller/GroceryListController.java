package com.fridgy.app.controller;

import com.fridgy.app.dto.GroceryListRequestDto;
import com.fridgy.app.dto.GroceryListResponseDto;
import com.fridgy.app.service.GroceryListService;
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

    @GetMapping("/{groceryListId}")
    ResponseEntity<GroceryListResponseDto> getGroceryList(@PathVariable Long groceryListId) {
        return ResponseEntity.ok(groceryListService.getGroceryList(groceryListId));
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

    // TODO: addItemToGroceryList and removeItemFromGroceryList - both of these will need the userId or HttpServletRequest

}
