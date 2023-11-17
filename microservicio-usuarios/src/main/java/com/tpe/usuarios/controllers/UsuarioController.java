package com.tpe.usuarios.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tpe.usuarios.AuthorityConstant;
import com.tpe.usuarios.models.dto.AuthDTO;
import com.tpe.usuarios.models.dto.UsuarioDTO;
import com.tpe.usuarios.security.JwtFilter;
import com.tpe.usuarios.security.TokenProvider;
import com.tpe.usuarios.services.UsuarioService;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    @Autowired
    private UsuarioService service;

    @PostMapping("/autenticar")
    public ResponseEntity<JwtToken> autenticarse(@Valid @RequestBody AuthDTO a){
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken( a.getEmail(), a.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final var jwt = tokenProvider.createToken (authentication );
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add( JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt );

        return new ResponseEntity<>(new JwtToken(jwt), httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/registrarse")
    public ResponseEntity<UsuarioDTO> register(@Valid @RequestBody UsuarioDTO u) throws Exception {
          UsuarioDTO us = this.service.save(u);
          return new ResponseEntity<>(us, HttpStatus.CREATED);
    }

    @Data
    @Builder
    public static class ValidateTokenDTO {
        private boolean isAuthenticated;
        private String username;
        private String authorities;
    }

    @GetMapping("/validate")
    public ResponseEntity<ValidateTokenDTO> validateGet() {
        final var user = SecurityContextHolder.getContext().getAuthentication();
        String authority = String.valueOf(user.getAuthorities().stream().findFirst());

        return ResponseEntity.ok(
                ValidateTokenDTO.builder()
                        .username(user.getName())
                        .authorities(authority)
                        .isAuthenticated(true)
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.ADMIN + "\" )")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.getById(id));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error. Por favor intente más tarde.");
        }
    }

    static class JwtToken {
        private String idToken;

        JwtToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }
}
