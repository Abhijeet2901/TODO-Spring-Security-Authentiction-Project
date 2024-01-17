package com.example.notes.model;

import lombok.Data;

@Data
public class AuthenticationTokenBody {
    private String email;
    private String password;
}
