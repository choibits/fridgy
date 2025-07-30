package com.fridgy.app.service;


import com.fridgy.app.dto.ItemRequestDto;
import com.fridgy.app.dto.ItemResponseDto;
import com.fridgy.app.dto.RefrigeratorRequestDto;
import com.fridgy.app.dto.RefrigeratorResponseDto;
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
public class RefrigeratorService implements IRefrigeratorService {
    @Autowired
    private RefrigeratorRepository refrigeratorRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public RefrigeratorResponseDto createRefrigerator(Long userId, RefrigeratorRequestDto dto) {
        // Check if user exists
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        Refrigerator refrigerator = modelMapper.map(dto, Refrigerator.class);

        List<User> users = refrigerator.getUsers();
        users.add(user);
        refrigerator.setUsers(users);

        Refrigerator savedRefrigerator = refrigeratorRepository.save(refrigerator);
        return modelMapper.map(savedRefrigerator, RefrigeratorResponseDto.class);
    }

    @Override
    public RefrigeratorResponseDto getRefrigeratorById(Long id) {
        Refrigerator refrigerator = refrigeratorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Refrigerator", "id", id));
        return modelMapper.map(refrigerator, RefrigeratorResponseDto.class);
    }

    @Override
    public List<RefrigeratorResponseDto> getAllRefrigerators(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        List<Refrigerator> refrigerators = user.getRefrigerators();
        return refrigerators.stream()
                .map(r -> modelMapper.map(r, RefrigeratorResponseDto.class))
                .toList();
    }

    @Override
    public RefrigeratorResponseDto updateRefrigerator(Long userId, Long refrigeratorId, RefrigeratorRequestDto refrigeratorRequestDto) {
        Refrigerator refrigerator = refrigeratorRepository.findById(refrigeratorId).orElseThrow(() -> new ResourceNotFoundException("Refrigerator", "id", refrigeratorId));
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        // Check user is one of the users of the fridge
        if (!refrigerator.getUsers().contains(user)) {
            throw new RuntimeException("User is not an owner of this fridge. Can't update fridge.");
        }
        // Only update relevant fields - not users so users can't accidentally overwrite over it
        refrigerator.setFridgeName(refrigeratorRequestDto.getFridgeName());

        return modelMapper.map(refrigeratorRepository.save(refrigerator), RefrigeratorResponseDto.class);
    }

    // TODO: Figure out where to implement this - request DTO doesn't currently take emails..
    //  would need to be input via frontend
    @Override
    public RefrigeratorResponseDto addUserToFridgeByEmail(Long userId, Long fridgeId, String email) {
        Refrigerator refrigerator = refrigeratorRepository.findById(fridgeId)
                .orElseThrow(() -> new ResourceNotFoundException("Refrigerator", "id", fridgeId));

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        User newUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));

        if (refrigerator.getUsers().contains(user)) {
            refrigerator.getUsers().add(newUser);
            refrigeratorRepository.save(refrigerator);
        }
        return modelMapper.map(refrigerator, RefrigeratorResponseDto.class);
    }

    @Override
    public RefrigeratorResponseDto removeUserFromFridgeByEmail(Long userId, Long fridgeId, String email) {
        Refrigerator refrigerator = refrigeratorRepository.findById(fridgeId)
                .orElseThrow(() -> new ResourceNotFoundException("Refrigerator", "id", fridgeId));
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        User userToRemove = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));

        if (refrigerator.getUsers().contains(user)) {
            refrigerator.getUsers().remove(userToRemove);
            refrigeratorRepository.save(refrigerator);
        }

        return modelMapper.map(refrigerator, RefrigeratorResponseDto.class);
    }

    @Override
    public void deleteRefrigerator(Long userId, Long refrigeratorId) {
        Refrigerator refrigerator = refrigeratorRepository.findById(refrigeratorId)
                .orElseThrow(() -> new ResourceNotFoundException("Refrigerator", "id", refrigeratorId));

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        if (refrigerator.getUsers().contains(user)) {
            refrigeratorRepository.delete(refrigerator);
        }
    }

    // ==== ITEMS ====
    @Override
    public List<ItemResponseDto> getItemsByFridgeId(Long fridgeId) {
        Refrigerator fridge = refrigeratorRepository.findById(fridgeId)
                .orElseThrow(() -> new ResourceNotFoundException("Refrigerator", "id", fridgeId));

        List<Item> items = itemRepository.findByRefrigeratorId(fridgeId);
        return items.stream()
                .map(item -> modelMapper.map(item, ItemResponseDto.class))
                .toList();
    }

    @Override
    public ItemResponseDto addItemToFridge(Long fridgeId, ItemRequestDto itemRequestDto) {
        Refrigerator fridge = refrigeratorRepository.findById(fridgeId)
                .orElseThrow(() -> new ResourceNotFoundException("Refrigerator", "id", fridgeId));

        Item item = modelMapper.map(itemRequestDto, Item.class);
        item.setRefrigerator(fridge);

        Item saved = itemRepository.save(item);
        return modelMapper.map(saved, ItemResponseDto.class);
    }

    @Override
    public ItemResponseDto updateItemInFridge(Long fridgeId, Long itemId, ItemRequestDto itemRequestDto) {
        Refrigerator fridge = refrigeratorRepository.findById(fridgeId)
                .orElseThrow(() -> new ResourceNotFoundException("Refrigerator", "id", fridgeId));

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item", "id", itemId));

        if (!item.getRefrigerator().getId().equals(fridge.getId())) {
            throw new IllegalArgumentException("Item does not belong to this fridge");
        }

        item.setItemName(itemRequestDto.getItemName());
        item.setExpirationDate(itemRequestDto.getExpirationDate());
        item.setQuantity(itemRequestDto.getQuantity());
//       TODO: handle images url
//        item.setImageUrl(itemRequestDto.getImageUrl());

        Item updated = itemRepository.save(item);
        return modelMapper.map(updated, ItemResponseDto.class);
    }

    @Override
    public void deleteItemFromFridge(Long fridgeId, Long itemId) {
        Refrigerator fridge = refrigeratorRepository.findById(fridgeId)
                .orElseThrow(() -> new ResourceNotFoundException("Refrigerator", "id", fridgeId));

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item", "id", itemId));

        if (!item.getRefrigerator().getId().equals(fridge.getId())) {
            throw new IllegalArgumentException("Item does not belong to this fridge");
        }

        itemRepository.delete(item);
    }
}
