package com.fridgy.app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
@Table(name="refrigerators")
public class Refrigerator {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    @NotBlank
    @Size(min=1, max=100)
    private String fridgeName;

    @ManyToMany
    @JoinTable(
            name="refrigerator_users",
            joinColumns = @JoinColumn(name="refrigerator_id"),
            inverseJoinColumns = @JoinColumn(name="user_id")
    )
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "refrigerator")
    private List<Item> items = new ArrayList<>();
}
