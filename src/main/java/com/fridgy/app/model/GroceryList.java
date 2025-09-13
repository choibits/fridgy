package com.fridgy.app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="grocery_lists")
public class GroceryList {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private Long id;

    // normally you would put the managed reference in the entity that has the mappedBy
    // since we're using dtos, we no longer need those
    @ManyToMany(mappedBy = "groceryLists")
    private List<User> users = new ArrayList<>();

    // bidirectional - can fetch items from the grocerylist, and the grocery list from the items
    @OneToMany(mappedBy = "groceryList", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> items = new ArrayList<>();

    @Column(nullable = false, length = 100)
    private String listName;

}
