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
            value = "SELECT f.* " +
                    "FROM user u " +
                    "JOIN fridge f1 ON u.fridge_id = f1.id " +
                    "JOIN fridge_content c1 ON f1.id = c1.fridge_id " +
                    "JOIN food f ON c1.content_id = f.id " +
                    "WHERE u.id = :userId " +
                    "AND (LOWER(f.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
                    "     LOWER(f.category) LIKE LOWER(CONCAT('%', :searchTerm, '%'))) " +
                    "ORDER BY " +
                    "CASE " +
                    "WHEN f.days_to_consume IS NOT NULL THEN DATE_ADD(CURRENT_DATE(), INTERVAL f.days_to_consume DAY) " +
                    "ELSE f.expire_date END ASC, " +
                    "f.expire_date ASC",
            countQuery = "SELECT COUNT(*) " +
                    "FROM user u " +
                    "JOIN fridge f1 ON u.fridge_id = f1.id " +
                    "JOIN fridge_content c1 ON f1.id = c1.fridge_id " +
                    "JOIN food f ON c1.content_id = f.id " +
                    "WHERE u.id = :userId " +
                    "AND (LOWER(f.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
                    "     LOWER(f.category) LIKE LOWER(CONCAT('%', :searchTerm, '%')))",
            nativeQuery = true
    )
    Page<Food> findAllByUserIdAndSearchTerm(Long userId,
                                            String searchTerm,
                                            Pageable pageable);

}
