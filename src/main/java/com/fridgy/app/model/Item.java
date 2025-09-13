package com.fridgy.app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="items")
public class Item {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "grocery_list_id")
    private GroceryList groceryList;

    @ManyToOne
    @JoinColumn(name="refrigerator_id")
    private Refrigerator refrigerator;

    @Column(nullable = false, length = 100)
    private String itemName;

    @Column(nullable = false) // isBought always starts as false?
    private boolean isBought;

    @Column(nullable = false)
    private int quantity;

    @Column
    private String imageUrl;

    @Column(nullable = false)
    private LocalDate expirationDate;
}
