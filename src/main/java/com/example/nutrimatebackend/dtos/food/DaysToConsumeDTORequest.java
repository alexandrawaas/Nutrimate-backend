package com.example.nutrimatebackend.dtos.food;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DaysToConsumeDTORequest
{
    public int daysToConsume;
    public LocalDateTime timeOpened;
}
