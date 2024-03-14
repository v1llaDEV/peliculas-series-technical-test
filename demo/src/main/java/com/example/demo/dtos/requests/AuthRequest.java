package com.example.demo.dtos.requests;

import lombok.Data;

@Data
public class AuthRequest {

    private String email;
    private String password;
}
