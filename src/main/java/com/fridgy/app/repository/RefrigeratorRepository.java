package com.fridgy.app.repository;

import com.fridgy.app.model.Refrigerator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefrigeratorRepository extends JpaRepository<Refrigerator, Long> {
    List<Refrigerator> findByUsers_Id(Long userId);
}
