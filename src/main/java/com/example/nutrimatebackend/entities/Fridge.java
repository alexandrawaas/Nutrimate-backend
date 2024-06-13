package com.example.nutrimatebackend.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Cascade;

import java.util.Collection;
import java.util.List;

@Data
@Entity
public class Fridge {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    List<Food> content;

    public Fridge(List<Food> content) {
        this.content = content;
    }


    public Fridge() {

    }
}
