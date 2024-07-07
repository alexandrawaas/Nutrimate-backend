package com.example.nutrimatebackend.dtos.user;

import com.example.nutrimatebackend.entities.User;
import org.springframework.stereotype.Service;

@Service
public class UserConverter
{
    public UserDTOResponse convertToUserDTOResponse(User user)
    {
        return new UserDTOResponse(user.getEmail());
    }

}
