package com.example.nutrimatebackend.dtos;

import lombok.Data;

@Data
public class EnvironmentalScoreDTO {
    int environmentalScore;

    public EnvironmentalScoreDTO(int environmentalScore) {
        this.environmentalScore = environmentalScore;
    }
}
