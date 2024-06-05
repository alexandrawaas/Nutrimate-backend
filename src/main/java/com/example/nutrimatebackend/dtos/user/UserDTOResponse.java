package com.example.nutrimatebackend.dtos.user;

import lombok.Data;

@Data
public class UserDTOResponse
{
    String eMail;

    public UserDTOResponse(String eMail)
    {
        this.eMail = eMail;
    }
}
