package com.fridgy.app.service;
import com.fridgy.app.dto.ItemRequestDto;
import com.fridgy.app.dto.ItemResponseDto;
import com.fridgy.app.exception.ResourceNotFoundException;
import com.fridgy.app.model.Item;
import com.fridgy.app.model.Refrigerator;
import com.fridgy.app.model.User;
import com.fridgy.app.repository.ItemRepository;
import com.fridgy.app.repository.RefrigeratorRepository;
import com.fridgy.app.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService implements IItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RefrigeratorRepository refrigeratorRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ItemResponseDto createItem(ItemRequestDto dto) {
        Item item = modelMapper.map(dto, Item.class);
        item.setBought(false);
        Item saved = itemRepository.save(item);
        return modelMapper.map(saved, ItemResponseDto.class);
    }

    @Override
    public ItemResponseDto getItemById(Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new ResourceNotFoundException("Item", "id", itemId));
        return modelMapper.map(item, ItemResponseDto.class);
    }

    @Override
    public List<ItemResponseDto> getAllItems() {
        List<Item> items = itemRepository.findAll();
        return items.stream().map(item -> modelMapper.map(item, ItemResponseDto.class)).toList();
    }

    @Override
    public ItemResponseDto updateItemById(Long userId, Long itemId, ItemRequestDto itemRequestDto) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new ResourceNotFoundException("Item", "id", itemId));
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        if (item.getGroceryList() != null) {
            if (!item.getGroceryList().getUsers().contains(user)) {
                throw new RuntimeException("This item belongs to another user's grocery list.");
            }
        } else if (item.getRefrigerator() != null) {
            boolean hasAccess = item.getRefrigerator().getUsers().stream()
                    .anyMatch(u -> u.getId().equals(userId));
            if (!hasAccess) {
                throw new RuntimeException("User doesn't have access to this refrigerator item.");
            }
        }

        modelMapper.map(itemRequestDto, item);
        Item updatedItem = itemRepository.save(item);
        return modelMapper.map(updatedItem, ItemResponseDto.class);
    }

    @Override
    public List<ItemResponseDto> moveItemsToFridge(Long userId, List<Long> itemIds, Long fridgeId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        Refrigerator fridge = refrigeratorRepository.findById(fridgeId)
                .orElseThrow(() -> new ResourceNotFoundException("Refrigerator", "id", fridgeId));

        // Check user has access to the target fridge
        if (fridge.getUsers() == null || fridge.getUsers().stream().noneMatch(u -> u.getId().equals(userId))) {
            throw new RuntimeException("User can't move items to a fridge they don't have access to.");
        }
        // Validate user can access the grocery list
        List<Item> items = itemRepository.findAllById(itemIds);
        for (Item item : items) {
            if (item.getGroceryList() != null &&
                    !item.getGroceryList().getUsers().contains(user)) {
                throw new RuntimeException("User can't move an item from another user's grocery list.");
            }
        }

        items.forEach(item -> {
            item.setBought(true);
            item.setRefrigerator(fridge);
        });

        List<Item> savedItems = itemRepository.saveAll(items);
        return savedItems.stream()
                .map(item -> modelMapper.map(item, ItemResponseDto.class))
                .toList();
    }


    @Override
    public void deleteItem(Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new ResourceNotFoundException("Item", "id", itemId));
            itemRepository.delete(item);
    }
}
