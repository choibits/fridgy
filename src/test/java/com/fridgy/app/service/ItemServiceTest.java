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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
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

        testRefrigerator = new Refrigerator();
        testRefrigerator.setId(1L);
    }

    @Test
    @DisplayName("Test createItem method - Happy Path")
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
    @DisplayName("Test createItem with null DTO - Sad Path")
    void testCreateItem_NullDto_ThrowsException() {
        assertThrows(NullPointerException.class, () -> itemService.createItem(null));
    }

    @Test
    @DisplayName("Test createItem with invalid DTO data - Sad Path")
    void testCreateItem_InvalidDtoData_ThrowsException() {
        // Set item name to null
        testItemRequestDto.setItemName(null);

        // Map DTO to Item entity
        Item invalidItem = new Item();
        when(modelMapper.map(testItemRequestDto, Item.class)).thenReturn(invalidItem);

        // Simulate repository throwing exception due to DB constraints
        when(itemRepository.save(invalidItem)).thenThrow(new RuntimeException("Not-null constraint violation"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> itemService.createItem(testItemRequestDto));
        assertEquals("Not-null constraint violation", exception.getMessage());
    }

    @Test
    @DisplayName("Test getItemById method - Happy Path")
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

}
