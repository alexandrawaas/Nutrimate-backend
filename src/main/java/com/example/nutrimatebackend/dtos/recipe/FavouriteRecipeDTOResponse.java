package com.example.nutrimatebackend.dtos.recipe;

public class FavouriteRecipeDTOResponse
{

    public Long id;
    public String url;
    public String name;

    public FavouriteRecipeDTOResponse(Long id, String url, String name)
    {
        this.id = id;
        this.url = url;
        this.name = name;
    }
}
