package com.fridgy.app.repository;

import com.fridgy.app.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByRefrigeratorId(Long fridgeId);
    // Custom queries can be added here if needed
}
