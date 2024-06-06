package com.example.nutrimatebackend.repositories;

import com.example.nutrimatebackend.entities.Allergen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AllergenRepository extends JpaRepository<Allergen, Long>, JpaSpecificationExecutor<Allergen> {
    Allergen findByNameIgnoreCase(String name);
}
