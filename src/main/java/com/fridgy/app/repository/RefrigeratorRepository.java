package com.fridgy.app.repository;

import com.fridgy.app.model.Refrigerator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefrigeratorRepository extends JpaRepository<Refrigerator, Long> {
    // Custom queries can be added here if needed
}
