package com.fridgy.app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
//keyword user is reserved in postgres so you need to rename the table
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    // UUIDs look like this: 123e4567-e89b-12d3-a456-426614174000

    @OneToOne(mappedBy="user", cascade = CascadeType.REMOVE)
    private Profile profile;

    // we could take this relationship out if we wanted to because if we did have many grocery lists
    // we might not always want to return all the grocery lists everytime we fetch a user
    @OneToMany
    private List<GroceryList> groceryLists;

    @ManyToMany(mappedBy = "users")
    private List<Refrigerator> refrigerators;

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @Column(nullable = false, length = 100)
    private String passwordHash;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private OffsetDateTime updatedAt;

}
