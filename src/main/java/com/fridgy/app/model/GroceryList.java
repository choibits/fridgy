package com.fridgy.app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    // can be bidirectional or unidirectional
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // normally you would put the managed reference in the entity that has the mappedBy
    // since we're using dtos, we no longer need those
    @ManyToMany
    @JoinTable(
            name="grocery_list_items",
            joinColumns = @JoinColumn(name="grocery_list_id"), // join grocery list id
            inverseJoinColumns = @JoinColumn(name="item_id") // on the other side item id
    )
    private List<Item> items;

    @Column(nullable = false, unique = true, length = 100)
    @NotBlank
    @Size(min=1, max=100)
    private String listName;

}
