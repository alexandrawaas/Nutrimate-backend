package com.example.nutrimatebackend.dtos.user;

public class UserDTORequest
{
    public String eMail;
    public String password;

    public UserDTORequest(String eMail, String password) {
        this.eMail = eMail;
        this.password = password;
    }
}
