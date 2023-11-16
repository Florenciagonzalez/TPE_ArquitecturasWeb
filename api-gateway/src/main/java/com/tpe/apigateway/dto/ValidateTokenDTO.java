package com.tpe.apigateway.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidateTokenDTO {
    private boolean isAuthenticated;
    private String username;
    private String authority;
}
