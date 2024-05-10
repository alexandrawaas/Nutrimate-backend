package com.example.nutrimatebackend.dtos;

public class CreateUserDTO {
    public String eMail;
    public String password;

    public CreateUserDTO(String eMail, String password) {
        this.eMail = eMail;
        this.password = password;
    }
}
