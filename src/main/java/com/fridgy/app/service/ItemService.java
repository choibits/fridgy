package com.fridgy.app.service;

import com.fridgy.app.dto.ItemRequestDto;
import com.fridgy.app.dto.ItemResponseDto;
import com.fridgy.app.exception.ResourceNotFoundException;
import com.fridgy.app.model.GroceryList;
import com.fridgy.app.model.Item;
import com.fridgy.app.model.Refrigerator;
import com.fridgy.app.model.User;
import com.fridgy.app.repository.GroceryListRepository;
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
    private GroceryListRepository groceryListRepository;

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

    // Note: you would update the item in the item details page, not the grocery list page
    // NOT like: /refrigerators/items/{itemId} or grocerylists/items/{itemId}
    // item id makes it unique - you would update on items/{itemId}

    @Override
    public ItemResponseDto updateItemById(Long itemId, ItemRequestDto itemRequestDto) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new ResourceNotFoundException("Item", "id", itemId));
        // an item can belong to a grocery list OR a refrigerator, so you don't need to check those exist
        modelMapper.map(itemRequestDto, item);
        Item updatedItem = itemRepository.save(item);
        return modelMapper.map(updatedItem, ItemResponseDto.class);
    }

//    @Override
//    public ItemResponseDto addItemToGroceryList(Long itemId, Long groceryListId) {
//        Item item = itemRepository.findById(itemId)
//                .orElseThrow(() -> new ResourceNotFoundException("Item", "id", itemId));
//
//        GroceryList list = groceryListRepository.findById(groceryListId)
//                .orElseThrow(() -> new ResourceNotFoundException("GroceryList", "id", groceryListId));
//
//        item.getGroceryLists().add(list);
//        list.getItems().add(item);
//
//        itemRepository.save(item);
//        groceryListRepository.save(list);
//
//        return modelMapper.map(item, ItemResponseDto.class);
//    }
//
//    @Override
//    public ItemResponseDto removeItemFromGroceryList(Long itemId, Long groceryListId) {
//        Item item = itemRepository.findById(itemId)
//                .orElseThrow(() -> new ResourceNotFoundException("Item", "id", itemId));
//
//        GroceryList list = groceryListRepository.findById(groceryListId)
//                .orElseThrow(() -> new ResourceNotFoundException("GroceryList", "id", groceryListId));
//
//        item.getGroceryLists().remove(list);
//        list.getItems().remove(item);
//        itemRepository.save(item);
//        groceryListRepository.save(list);
//        return modelMapper.map(item, ItemResponseDto.class);
//    }
//
//    @Override
//    public ItemResponseDto moveItemToFridge(Long itemId, Long fridgeId) {
//        Item item = itemRepository.findById(itemId)
//                .orElseThrow(() -> new ResourceNotFoundException("Item", "id", itemId));
//
//        Refrigerator fridge = refrigeratorRepository.findById(fridgeId)
//                .orElseThrow(() -> new ResourceNotFoundException("Refrigerator", "id", fridgeId));
//
//        item.setBought(true);
//        item.setRefrigerator(fridge);
//
//        itemRepository.save(item);
//        return modelMapper.map(item, ItemResponseDto.class);
//    }

    @Override
    public void deleteItem(Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new ResourceNotFoundException("Item", "id", itemId));
            itemRepository.delete(item);
    }
}
