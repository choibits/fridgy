package com.fridgy.app.controller;

import com.fridgy.app.dto.ItemRequestDto;
import com.fridgy.app.dto.ItemResponseDto;
import com.fridgy.app.service.IItemService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
@CrossOrigin("*")
public class ItemController {

    @Autowired
    private IItemService itemService;

    @PostMapping
    public ResponseEntity<ItemResponseDto> createItem(@Valid @RequestBody ItemRequestDto itemRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(itemService.createItem(itemRequestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemResponseDto> getItemById(@PathVariable Long id) {
        return ResponseEntity.ok(itemService.getItemById(id));
    }

    // TODO: Not sure if I need this endpoint
    @GetMapping
    public ResponseEntity<List<ItemResponseDto>> getAllItems() {
        return ResponseEntity.ok(itemService.getAllItems());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemResponseDto> updateItem(@PathVariable Long id,
                                                      @Valid @RequestBody ItemRequestDto itemRequestDto) {
        return ResponseEntity.accepted().body(itemService.updateItemById(id, itemRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }
}
