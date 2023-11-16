package com.tpe.usuarios.models.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AuthDTO {
    private String email;
    private String password;
}
