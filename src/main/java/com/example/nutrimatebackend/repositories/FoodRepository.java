package com.example.nutrimatebackend.repositories;

import com.example.nutrimatebackend.entities.Food;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long>, JpaSpecificationExecutor<Food> {
    Optional<Food> findByBarcode(String barcode);
    @Query(
            value = "SELECT user.fridge.content FROM User user WHERE user.id = :userId",
            countQuery = "SELECT elements(user.fridge.content) FROM User user WHERE user.id = :userId"
    )
    Page<Food> findAllByUserId(@Param("userId") Long userId, Pageable pageable);

}
