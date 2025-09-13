package com.fridgy.app.service;

import com.fridgy.app.dto.GroceryListRequestDto;
import com.fridgy.app.dto.GroceryListResponseDto;
import com.fridgy.app.dto.ItemRequestDto;
import com.fridgy.app.dto.ItemResponseDto;
import com.fridgy.app.exception.ResourceNotFoundException;
import com.fridgy.app.model.GroceryList;
import com.fridgy.app.model.Item;
import com.fridgy.app.model.User;
import com.fridgy.app.repository.GroceryListRepository;
import com.fridgy.app.repository.ItemRepository;
import com.fridgy.app.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class GroceryListService implements IGroceryListService {

    @Autowired
    private GroceryListRepository groceryListRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public GroceryListResponseDto createGroceryList(Long userId, GroceryListRequestDto requestDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        GroceryList groceryList = modelMapper.map(requestDto, GroceryList.class);
        GroceryList savedGroceryList = groceryListRepository.save(groceryList);
        user.getGroceryLists().add(savedGroceryList);
        userRepository.save(user);
        return modelMapper.map(savedGroceryList, GroceryListResponseDto.class);
    }

    @Override
    public GroceryListResponseDto getGroceryList(Long groceryListId) {
        GroceryList groceryList = groceryListRepository.findById(groceryListId).orElseThrow(() -> new ResourceNotFoundException("GroceryList", "id", groceryListId));
        return modelMapper.map(groceryList, GroceryListResponseDto.class);
    }

    @Override
    public List<GroceryListResponseDto> getGroceryListsByUserId(Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        List<GroceryList> groceryLists = groceryListRepository.findAllByUsers_Id(userId);
        return groceryLists.stream().map(groceryList -> modelMapper.map(groceryList, GroceryListResponseDto.class)).toList();
    }

    @Override
    public List<ItemResponseDto> getItemsByGroceryListId(Long groceryListId) {
        List<Item> items = groceryListRepository.findItemsByGroceryListId(groceryListId);
        return items.stream()
                .map(item -> modelMapper.map(item, ItemResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public GroceryListResponseDto updateGroceryList(Long userId, Long groceryListId, GroceryListRequestDto requestDto) {
        GroceryList groceryList = groceryListRepository.findById(groceryListId).orElseThrow(() -> new ResourceNotFoundException("GroceryList", "id", groceryListId));
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        if (!groceryList.getUsers().contains(user)) {
            throw new RuntimeException("The grocery list belongs to another user. Can't update it.");
        }
        modelMapper.map(requestDto, groceryList);
        GroceryList updatedGroceryList = groceryListRepository.save(groceryList);
        return modelMapper.map(updatedGroceryList, GroceryListResponseDto.class);
    }

    @Override
    public ItemResponseDto createItemForGroceryList(Long userId, Long groceryListId, ItemRequestDto itemRequestDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        GroceryList groceryList = groceryListRepository.findById(groceryListId)
                .orElseThrow(() -> new ResourceNotFoundException("GroceryList", "id", groceryListId));
        if (!groceryList.getUsers().contains(user)) {
            throw new RuntimeException("The grocery list belongs to another user. Can't add items to it.");
        }

        Item newItem = modelMapper.map(itemRequestDto, Item.class);
        newItem.setGroceryList(groceryList);
        Item savedItem = itemRepository.save(newItem);

        groceryList.getItems().add(savedItem);
        groceryListRepository.save(groceryList);
        return modelMapper.map(savedItem, ItemResponseDto.class);
    }

    @Override
    public ItemResponseDto updateGroceryListItem(Long listId, Long itemId, ItemRequestDto itemRequestDto) {
        groceryListRepository.findById(listId).orElseThrow(() -> new ResourceNotFoundException("GroceryList", "id",
                listId));

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item", "id", itemId));

        item.setItemName(itemRequestDto.getItemName());
        item.setQuantity(itemRequestDto.getQuantity());
        item.setExpirationDate(itemRequestDto.getExpirationDate());

        Item updatedItem = itemRepository.save(item);
        return modelMapper.map(updatedItem, ItemResponseDto.class);
    }

    @Override
    public GroceryListResponseDto removeItemFromGroceryList(Long userId, Long groceryListId, Long itemId) {
        GroceryList groceryList = groceryListRepository.findById(groceryListId).orElseThrow(() -> new ResourceNotFoundException("GroceryList", "id", groceryListId));
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item", "id", itemId));
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        if (!groceryList.getUsers().contains(user)) {
            throw new RuntimeException("The grocery list belongs to another user. Can't remove items from it.");
        }

        // Breaks the relationship from the owning side
        // 	orphanRemoval = true → Hibernate will automatically delete items removed from the parent’s list
        // 	(but only if the owning side is also updated, i.e., item.setGroceryList(null) happens).
        groceryList.getItems().remove(item);
        itemRepository.delete(item);

        return modelMapper.map(groceryList, GroceryListResponseDto.class);
    }

    @Override
    public void deleteGroceryList(Long userId, Long groceryListId) {
        GroceryList groceryList = groceryListRepository.findById(groceryListId).orElseThrow(() -> new ResourceNotFoundException("GroceryList", "id", groceryListId));
        User userToCheck = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        if (!groceryList.getUsers().contains(userToCheck)) {
            throw new RuntimeException("The grocery list belongs to another user. Can't delete it.");
        }
        for (User user: groceryList.getUsers()) {
            user.getGroceryLists().remove(groceryList);
            userRepository.save(user);
        }
        itemRepository.deleteAll(groceryList.getItems());
        groceryListRepository.delete(groceryList);
    }
}
