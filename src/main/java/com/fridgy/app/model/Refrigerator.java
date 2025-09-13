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

    @Column(nullable = false, length = 100)
    @NotBlank
    @Size(min=1, max=100)
    private String fridgeName;

    @ManyToMany(mappedBy = "refrigerators")
    private List<User> users = new ArrayList<>();

    // side with mappedBy is the inverse (non-owning) side of the relationship
    @OneToMany(mappedBy = "refrigerator")
    private List<Item> items = new ArrayList<>();
}
