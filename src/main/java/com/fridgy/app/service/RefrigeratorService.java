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
    public RefrigeratorResponseDto createRefrigerator(RefrigeratorRequestDto dto) {
        // Map DTO to entity
        Refrigerator refrigerator = new Refrigerator();
        refrigerator.setFridgeName(dto.getFridgeName());

        // Fetch users and add to fridge
        if (dto.getUserIds() != null && !dto.getUserIds().isEmpty()) {
            List<User> users = userRepository.findAllById(dto.getUserIds());
            refrigerator.setUsers(users);
        }

        // Save and return response
        Refrigerator saved = refrigeratorRepository.save(refrigerator);
        return modelMapper.map(saved, RefrigeratorResponseDto.class);
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

        List<Refrigerator> refrigerators = refrigeratorRepository.findAllByUserId(userId);
        return refrigerators.stream()
                .map(r -> modelMapper.map(r, RefrigeratorResponseDto.class))
                .toList();
    }

    @Override
    public RefrigeratorResponseDto updateRefrigerator(Long refrigeratorId, RefrigeratorRequestDto refrigeratorRequestDto) {
        Refrigerator refrigerator = refrigeratorRepository.findById(refrigeratorId).orElseThrow(() -> new ResourceNotFoundException("Refrigerator", "id", refrigeratorId));

        // Only update relevant fields - not users so users can't accidentally overwrite over it
        refrigerator.setFridgeName(refrigeratorRequestDto.getFridgeName());

        return modelMapper.map(refrigeratorRepository.save(refrigerator), RefrigeratorResponseDto.class);
    }

    @Override
    public RefrigeratorResponseDto addUserToFridgeByEmail(Long fridgeId, String email) {
        Refrigerator fridge = refrigeratorRepository.findById(fridgeId)
                .orElseThrow(() -> new ResourceNotFoundException("Refrigerator", "id", fridgeId));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));

        if (!fridge.getUsers().contains(user)) {
            fridge.getUsers().add(user);
            refrigeratorRepository.save(fridge);
        }
        return modelMapper.map(fridge, RefrigeratorResponseDto.class);
    }

    @Override
    public RefrigeratorResponseDto removeUserFromFridgeByEmail(Long fridgeId, String email) {
        Refrigerator fridge = refrigeratorRepository.findById(fridgeId)
                .orElseThrow(() -> new ResourceNotFoundException("Refrigerator", "id", fridgeId));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));

        if (fridge.getUsers().contains(user)) {
            fridge.getUsers().remove(user);
            refrigeratorRepository.save(fridge);
        }

        return modelMapper.map(fridge, RefrigeratorResponseDto.class);
    }

    @Override
    public void deleteRefrigerator(Long refrigeratorId) {
        Refrigerator refrigerator = refrigeratorRepository.findById(refrigeratorId)
                .orElseThrow(() -> new ResourceNotFoundException("Refrigerator", "id", refrigeratorId));
        refrigeratorRepository.delete(refrigerator);
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
