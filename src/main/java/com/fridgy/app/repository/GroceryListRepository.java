package com.fridgy.app.repository;

import com.fridgy.app.model.GroceryList;
import com.fridgy.app.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroceryListRepository extends JpaRepository<GroceryList, Long> {
    List<GroceryList> findAllByUserId(Long userId);
    @Query("SELECT g.items FROM GroceryList g WHERE g.id = :id")
    List<Item> findItemsByGroceryListId(@Param("id") Long id);

    // Custom queries can be added here if needed
}
