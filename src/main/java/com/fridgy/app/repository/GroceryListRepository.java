package com.fridgy.app.repository;

import com.fridgy.app.model.GroceryList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroceryListRepository extends JpaRepository<GroceryList, Long> {
    // Custom queries can be added here if needed
}
