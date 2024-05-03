package com.example.nutrimatebackend.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Fridge {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @OneToMany(fetch = FetchType.EAGER)
    List<Food> content;

    public Fridge(List<Food> content) {
        this.content = content;
    }


    public Fridge() {

    }
}
