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
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new ResourceNotFoundException("Item", "id", itemId));
        // an item can belong to a grocery list OR a refrigerator, so you don't need to check those exist
        modelMapper.map(itemRequestDto, item);
        Item updatedItem = itemRepository.save(item);
        return modelMapper.map(updatedItem, ItemResponseDto.class);
    }

    @Override
    public List<ItemResponseDto> moveItemsToFridge(List<Long> itemIds, Long fridgeId) {
        Refrigerator fridge = refrigeratorRepository.findById(fridgeId)
                .orElseThrow(() -> new ResourceNotFoundException("Refrigerator", "id", fridgeId));

        List<Item> items = itemRepository.findAllById(itemIds);

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
