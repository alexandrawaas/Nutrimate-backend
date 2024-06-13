package com.example.nutrimatebackend.repositories;

import com.example.nutrimatebackend.entities.Food;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long>, JpaSpecificationExecutor<Food> {
    Optional<Food> findByBarcode(String barcode);

    Page<Food> findAllByFridge_Id(Long fridgeId, Pageable pageable);

}
