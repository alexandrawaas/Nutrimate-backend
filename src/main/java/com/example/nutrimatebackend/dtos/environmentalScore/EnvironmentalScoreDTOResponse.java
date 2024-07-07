package com.example.nutrimatebackend.dtos.environmentalScore;

import lombok.Data;

@Data
public class EnvironmentalScoreDTOResponse
{
    int environmentalScore;

    public EnvironmentalScoreDTOResponse(int environmentalScore) {
        this.environmentalScore = environmentalScore;
    }
}
