package com.fridgy.app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name="user_id", nullable = false) // even if we don't provide this hibernate will provide
    private User user;

    @NotBlank
    @Size(min=1, max=100)
    @Column(nullable = false, length = 100)
    private String firstName;

    @NotBlank
    @Size(min=1, max=100)
    @Column(nullable = false, length = 100)
    private String lastName;

    // optional favorite foods field
    @Size(max=200)
    @Column(length = 200)
    private String favoriteFoods;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private OffsetDateTime updatedAt;
}
