package com.fridgy.app.controller;

import com.fridgy.app.dto.ItemRequestDto;
import com.fridgy.app.dto.ItemResponseDto;
import com.fridgy.app.dto.RefrigeratorRequestDto;
import com.fridgy.app.dto.RefrigeratorResponseDto;
import com.fridgy.app.repository.ItemRepository;
import com.fridgy.app.service.IItemService;
import com.fridgy.app.service.IRefrigeratorService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/refrigerators")
@CrossOrigin("*")
public class RefrigeratorController {

    @Autowired
    private IRefrigeratorService refrigeratorService;

    @Autowired
    private IItemService itemService;

    @Autowired
    private ItemRepository itemRepository;

    @PostMapping
    public ResponseEntity<RefrigeratorResponseDto> createRefrigerator(HttpServletRequest request,
                                                                   @Valid @RequestBody RefrigeratorRequestDto requestDto) {
        Long userId = (Long) request.getAttribute("userId");
        return ResponseEntity.status(HttpStatus.CREATED).body(refrigeratorService.createRefrigerator(userId, requestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RefrigeratorResponseDto> getRefrigeratorById(@PathVariable Long id) {
        return ResponseEntity.ok().body(refrigeratorService.getRefrigeratorById(id));
    }

    @GetMapping
    public ResponseEntity<List<RefrigeratorResponseDto>> getAllRefrigeratorsByUserId(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return ResponseEntity.ok().body(refrigeratorService.getAllRefrigerators(userId));
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<RefrigeratorResponseDto>> getRefrigeratorsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(refrigeratorService.getRefrigeratorsByUserId(userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RefrigeratorResponseDto> updateRefrigerator(
            HttpServletRequest request,
            @PathVariable Long id,
            @Valid @RequestBody RefrigeratorRequestDto requestDto) {
        Long userId = (Long) request.getAttribute("userId");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(refrigeratorService.updateRefrigerator(userId, id, requestDto));
    }

    @PostMapping("/{id}/add-user")
    public ResponseEntity<RefrigeratorResponseDto> addUserToFridgeByEmail(
            HttpServletRequest request,
            @PathVariable Long id,
            @RequestParam String email) {
        Long userId = (Long) request.getAttribute("userId");
        RefrigeratorResponseDto responseDto = refrigeratorService.addUserToFridgeByEmail(userId, id, email);
        return ResponseEntity.ok().body(responseDto);
    }

    @DeleteMapping("/{id}/remove-user")
    public ResponseEntity<RefrigeratorResponseDto> removeUserFromFridgeByEmail(
            HttpServletRequest request,
            @PathVariable Long id,
            @RequestParam String email) {
        Long userId = (Long) request.getAttribute("userId");
        RefrigeratorResponseDto responseDto = refrigeratorService.removeUserFromFridgeByEmail(userId, id, email);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRefrigerator(HttpServletRequest request, @PathVariable Long id) {
        Long userId = (Long) request.getAttribute("userId");
        refrigeratorService.deleteRefrigerator(userId, id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // TODO: Implement these endpoints if I need them
    // ==== ITEMS ====
//    @GetMapping
//    public ResponseEntity<List<ItemResponseDto>> getItemsInFridge(@PathVariable Long fridgeId) {
//        return ResponseEntity.ok(itemService.getItems(fridgeId));
//    }

    @PostMapping("/{fridgeId}/items")
    public ResponseEntity<List<ItemResponseDto>> addItemsToFridge(
            @PathVariable Long fridgeId,
            @RequestBody List<Long> itemIds) {  // Accept item IDs, not full DTOs
        return ResponseEntity.ok(itemService.moveItemsToFridge(itemIds, fridgeId));
    }
//
//    @PutMapping("/{itemId}")
//    public ResponseEntity<ItemResponseDto> updateItemInFridge(@PathVariable Long fridgeId,
//                                                              @PathVariable Long itemId,
//                                                              @Valid @RequestBody ItemRequestDto itemDto) {
//        return ResponseEntity.ok(itemService.updateItemInFridge(fridgeId, itemId, itemDto));
//    }
//
//    @DeleteMapping("/{itemId}")
//    public ResponseEntity<Void> deleteItemFromFridge(@PathVariable Long fridgeId,
//                                                     @PathVariable Long itemId) {
//        itemService.deleteItemFromFridge(fridgeId, itemId);
//        return ResponseEntity.noContent().build();
//    }
}