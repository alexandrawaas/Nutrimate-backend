package com.example.nutrimatebackend.repositories;

import com.example.nutrimatebackend.entities.Fridge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FridgeRepository extends JpaRepository<Fridge, Long>, JpaSpecificationExecutor<Fridge> {
}
