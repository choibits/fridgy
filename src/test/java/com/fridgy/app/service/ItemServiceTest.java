package com.fridgy.app.service;

import com.fridgy.app.dto.ItemRequestDto;
import com.fridgy.app.dto.ItemResponseDto;
import com.fridgy.app.exception.ResourceNotFoundException;
import com.fridgy.app.model.GroceryList;
import com.fridgy.app.model.Item;
import com.fridgy.app.model.Refrigerator;
import com.fridgy.app.model.User;
import com.fridgy.app.repository.ItemRepository;
import com.fridgy.app.repository.RefrigeratorRepository;
import com.fridgy.app.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private RefrigeratorRepository refrigeratorRepository;

    @Mock
    private UserRepository userRepository;

     @InjectMocks
     private ItemService itemService;

    private Item testItem;
    private ItemRequestDto testItemRequestDto;
    private ItemResponseDto testItemResponseDto;
    private User testUser;
    private Refrigerator testRefrigerator;
    private User unauthorizedUser;
    private GroceryList testGroceryList;

    @BeforeEach
    void setUp() {
        testItem = new Item();
        testItem.setId(1L);
        testItem.setItemName("Test Item");
        testItem.setBought(false);
        testItem.setQuantity(1);

        testItemRequestDto = new ItemRequestDto();
        testItemRequestDto.setItemName("Test Item");
        testItemRequestDto.setQuantity(1);

        testItemResponseDto = new ItemResponseDto();
        testItemResponseDto.setId(1L);
        testItemResponseDto.setItemName("Test Item");
        testItemResponseDto.setBought(false);
        testItemResponseDto.setQuantity(1);

        testUser = new User();
        testUser.setId(1L);

        unauthorizedUser = new User();
        unauthorizedUser.setId(3L);

        testGroceryList = new GroceryList();
        testGroceryList.setId(1L);
        testGroceryList.getUsers().add(testUser);
        testItem.setGroceryList(testGroceryList);

        testRefrigerator = new Refrigerator();
        testRefrigerator.setId(1L);
        testRefrigerator.getUsers().add(testUser);
        testItem.setRefrigerator(testRefrigerator);
    }

    @Test
    @DisplayName("Test createItem - Happy Path")
    void testCreateItem_Success() {
        when(modelMapper.map(testItemRequestDto, Item.class)).thenReturn(testItem);
        when(itemRepository.save(testItem)).thenReturn(testItem);
        when(modelMapper.map(testItem, ItemResponseDto.class)).thenReturn(testItemResponseDto);

        ItemResponseDto result = itemService.createItem(testItemRequestDto);

        assertNotNull(result);
        assertEquals(testItem.getId(), result.getId());
        assertEquals(testItem.getItemName(), result.getItemName());
        assertEquals(testItem.getQuantity(), result.getQuantity());
        assertEquals(testItem.getExpirationDate(), result.getExpirationDate());
        assertFalse(result.isBought());
        verify(itemRepository, times(1)).save(testItem);
    }

    @Test
    @DisplayName("Test createItem - Sad Path")
    void testCreateItem_DBError() {
        when(modelMapper.map(testItemRequestDto, Item.class)).thenReturn(testItem);
        when(itemRepository.save(testItem)).thenThrow(new RuntimeException("DB Error"));
        assertThrows(RuntimeException.class, () -> itemService.createItem(testItemRequestDto));
    }

    @Test
    @DisplayName("Test getItemById - Happy Path")
    void testGetItemById_Success() {
        when(itemRepository.findById(1L)).thenReturn(Optional.of(testItem));
        when(modelMapper.map(testItem, ItemResponseDto.class)).thenReturn(testItemResponseDto);

        ItemResponseDto result = itemService.getItemById(1L);

        assertNotNull(result);
        assertEquals(testItem.getId(), result.getId());
        assertEquals(testItem.getItemName(), result.getItemName());
        assertEquals(testItem.getQuantity(), result.getQuantity());
        assertEquals(testItem.getExpirationDate(), result.getExpirationDate());
        assertFalse(result.isBought());
    }

    @Test
    @DisplayName("Test getItemById - Item not found")
    void testGetItemById_ItemNotFound() {
        when(itemRepository.findById(2L)).thenThrow(new ResourceNotFoundException("Item", "id", 2L));
        assertThrows(ResourceNotFoundException.class, () -> itemService.getItemById(2L));
    }

    @Test
    @DisplayName("Test getAllItems - Happy Path")
    void testGetAllItems_Success() {
        when(itemRepository.findAll()).thenReturn(java.util.List.of(testItem));
        when(modelMapper.map(testItem, ItemResponseDto.class)).thenReturn(testItemResponseDto);

        List<ItemResponseDto> result = itemService.getAllItems();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testItem.getId(), result.get(0).getId());
    }

    @Test
    @DisplayName("Test getAllItems - Sad Path")
    void testGetAllItems_DBError() {
        when(itemRepository.findAll()).thenThrow(new RuntimeException("DB Error"));
        assertThrows(RuntimeException.class, () -> itemService.getAllItems());
    }

    @Test
    @DisplayName("Test updateItemById - Happy Path")
    void testUpdateItemById_Success() {
        when(itemRepository.findById(1L)).thenReturn(Optional.of(testItem));
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        // Mockito's when + then return only works for methods that return something.
        // For void methods, use doNothing()
        doNothing().when(modelMapper).map(testItemRequestDto, testItem);
        when(itemRepository.save(testItem)).thenReturn(testItem);
        when(modelMapper.map(testItem, ItemResponseDto.class)).thenReturn(testItemResponseDto);

        ItemResponseDto result = itemService.updateItemById(1L, 1L, testItemRequestDto);

        assertNotNull(result);
        assertEquals(testItem.getId(), result.getId());
        assertEquals(testItem.getItemName(), result.getItemName());
        assertEquals(testItem.getQuantity(), result.getQuantity());
        assertEquals(testItem.getExpirationDate(), result.getExpirationDate());
        assertFalse(result.isBought());
    }

    @Test
    @DisplayName("Test updateItemById - Item not found")
    void testUpdateItemById_ItemNotFound() {
        when(itemRepository.findById(2L)).thenThrow(new ResourceNotFoundException("Item", "id", 2L));
        assertThrows(ResourceNotFoundException.class, () -> itemService.updateItemById(1L, 2L, testItemRequestDto));
    }

    @Test
    @DisplayName("Test updateItemById - User not found")
    void testUpdateItemById_UserNotFound() {
        when(itemRepository.findById(1L)).thenReturn(Optional.of(testItem));
        when(userRepository.findById(2L)).thenThrow(new ResourceNotFoundException("User", "id", 2L));
        assertThrows(ResourceNotFoundException.class, () -> itemService.updateItemById(2L, 1L, testItemRequestDto));
    }

    @Test
    @DisplayName("Test updateItemById - User not authorized")
    void testUpdateItemById_UserNotAuthorized() {
        when(itemRepository.findById(1L)).thenReturn(Optional.of(testItem));
        when(userRepository.findById(3L)).thenReturn(Optional.of(unauthorizedUser));
        assertThrows(RuntimeException.class, () -> itemService.updateItemById(3L, 1L, testItemRequestDto));
    }

//    @Test
//    @DisplayName("Test moveItemsToFridge - Happy Path")
//    void testMoveItemsToFridge_Success() {
//        when(refrigeratorRepository.findById(1L)).thenReturn(Optional.of(testRefrigerator));
//        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
//        when(modelMapper.map(testItem, ItemResponseDto.class)).thenReturn(testItemResponseDto);
//
//    }
}
