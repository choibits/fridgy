package com.fridgy.app.service;

import com.fridgy.app.dto.GroceryListRequestDto;
import com.fridgy.app.dto.GroceryListResponseDto;
import com.fridgy.app.exception.ResourceNotFoundException;
import com.fridgy.app.model.GroceryList;
import com.fridgy.app.model.User;
import com.fridgy.app.repository.GroceryListRepository;
import com.fridgy.app.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroceryListService implements IGroceryListService {

    @Autowired
    private GroceryListRepository groceryListRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Override
    public GroceryListResponseDto createGroceryList(Long userId, GroceryListRequestDto requestDto) {
        // Check if user exists
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        GroceryList groceryList = modelMapper.map(requestDto, GroceryList.class);
        groceryList.setUser(user);
        GroceryList savedGroceryList = groceryListRepository.save(groceryList);
        return modelMapper.map(savedGroceryList, GroceryListResponseDto.class);
    }

    @Override
    public GroceryListResponseDto getGroceryList(Long groceryListId) {
        GroceryList groceryList = groceryListRepository.findById(groceryListId).orElseThrow(() -> new ResourceNotFoundException("GroceryList", "id", groceryListId));
        return modelMapper.map(groceryList, GroceryListResponseDto.class);
    }

    @Override
    public List<GroceryListResponseDto> getAllGroceryListsByUserId(Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        List<GroceryList> groceryLists = groceryListRepository.findAllByUserId(userId);
        return groceryLists.stream().map(groceryList -> modelMapper.map(groceryList, GroceryListResponseDto.class)).toList();
    }

    @Override
    public GroceryListResponseDto updateGroceryList(Long userId, Long groceryListId, GroceryListRequestDto requestDto) {
        GroceryList groceryList = groceryListRepository.findById(groceryListId).orElseThrow(() -> new ResourceNotFoundException("GroceryList", "id", groceryListId));
        userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        if (groceryList.getUser().getId() != userId) {
            throw new RuntimeException("The grocery list belongs to another user. Can't update it.");
        }
        modelMapper.map(requestDto, groceryList);
        GroceryList updatedGroceryList = groceryListRepository.save(groceryList);
        return modelMapper.map(updatedGroceryList, GroceryListResponseDto.class);
    }

    @Override
    public void deleteGroceryList(Long userId, Long groceryListId) {
        GroceryList groceryList = groceryListRepository.findById(groceryListId).orElseThrow(() -> new ResourceNotFoundException("GroceryList", "id", groceryListId));
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        if (groceryList.getUser().getId() != userId) {
            throw new RuntimeException("The grocery list belongs to another user. Can't delete it.");
        }
        groceryListRepository.delete(groceryList);
    }
}
