package com.fridgy.app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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

    @ManyToMany(mappedBy="items") // other side will then create the join table
    private List<GroceryList> groceryLists;

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
