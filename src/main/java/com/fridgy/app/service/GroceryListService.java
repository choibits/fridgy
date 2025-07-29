package com.fridgy.app.service;

import com.fridgy.app.dto.GroceryListRequestDto;
import com.fridgy.app.dto.GroceryListResponseDto;
import com.fridgy.app.exception.ResourceNotFoundException;
import com.fridgy.app.model.GroceryList;
import com.fridgy.app.model.User;
import com.fridgy.app.repository.GroceryListRepository;
import com.fridgy.app.repository.UserRepository;
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
    public GroceryListResponseDto createGroceryList(GroceryListRequestDto requestDto) {
        // Check if user exists
        User user = userRepository.findById(requestDto.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User", "id", requestDto.getUserId()));

        GroceryList groceryList = modelMapper.map(requestDto, GroceryList.class);
        groceryList.setUser(user);
        GroceryList savedGroceryList = groceryListRepository.save(groceryList);
        return modelMapper.map(savedGroceryList, GroceryListResponseDto.class);
    }

    @Override
    public GroceryListResponseDto getGroceryList(Long groceryListId) {
        return null;
    }

    @Override
    public List<GroceryListResponseDto> getAllGroceryListsByUserId(Long userId) {
        return List.of();
    }

    @Override
    public GroceryListResponseDto updateGroceryList(Long groceryListId, GroceryListRequestDto requestDto) {
        return null;
    }

    @Override
    public void deleteGroceryList(Long groceryListId) {

    }
}
