package com.fridgy.app.config;

import com.fridgy.app.dto.*;
import com.fridgy.app.repository.*;
import com.fridgy.app.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataSeeder implements CommandLineRunner {
    // ==== Services ====
    @Autowired
    private AuthService authService;

    @Autowired
    private GroceryListService groceryListService;

    @Autowired
    private RefrigeratorService refrigeratorService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private ProfileService profileService;

    // ==== Repositories ====
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroceryListRepository groceryListRepository;

    @Autowired
    private RefrigeratorRepository refrigeratorRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Data Seeder is running...");
        if (userRepository.count() == 0
        && groceryListRepository.count() == 0
        && refrigeratorRepository.count() == 0
        && profileRepository.count() == 0
        && itemRepository.count() == 0) {
            seedData();
            System.out.println("Data Seeder has seeded the database.");
        } else {
            System.out.println("Data already exists - skipping seeding.");
        }
    }

    // ==== Data Seeder ====
    private void seedData() {
        // ==== USERS ====
        AuthRequestDto user1Dto = AuthRequestDto.builder()
                .email("user1@example.com")
                .password("Password123")
                .build();
        AuthResponseDto savedUser1 = authService.signup(user1Dto);
        System.out.println("User1 ID: " + savedUser1.getId()); // Add thi

        AuthRequestDto user2Dto = AuthRequestDto.builder()
                .email("user2@example.com")
                .password("Password123")
                .build();
        AuthResponseDto savedUser2 = authService.signup(user2Dto);

        // ==== GROCERY LISTS ====
        GroceryListRequestDto groceryListUser1Dto = GroceryListRequestDto.builder()
                .listName("Trader Joe's")
                .build();
        groceryListService.createGroceryList(savedUser1.getId(), groceryListUser1Dto);

        GroceryListRequestDto groceryListUser2Dto = GroceryListRequestDto.builder()
                .listName("Hmart")
                .build();
        groceryListService.createGroceryList(savedUser2.getId(), groceryListUser2Dto);

        GroceryListRequestDto groceryListUser2Dto2 = GroceryListRequestDto.builder()
                .listName("Safeway")
                .build();
        groceryListService.createGroceryList(savedUser2.getId(), groceryListUser2Dto2);

        // ==== REFRIGERATORS ====
        RefrigeratorRequestDto fridge1Dto = RefrigeratorRequestDto.builder()
                .fridgeName("Kitchen Fridge")
                .build();
        refrigeratorService.createRefrigerator(savedUser1.getId(), fridge1Dto);

        RefrigeratorRequestDto fridge2Dto = RefrigeratorRequestDto.builder()
                .fridgeName("Garage Fridge")
                .build();
        refrigeratorService.createRefrigerator(savedUser2.getId(), fridge2Dto);

        // ==== ITEMS ====
        ItemRequestDto item1Dto = ItemRequestDto.builder()
                .itemName("Milk")
                .quantity(2)
                .expirationDate(LocalDate.of(2025, 8, 10))
                .build();
        // Add to grocery list or refrigerator
        // groceryListService.addItemToList(savedGroceryList.getId(), item1Dto);
        // or
        // refrigeratorService.addItemToFridge(savedFridge.getId(), item1Dto);

    }
}
