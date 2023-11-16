package com.tpe.usuarios.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Rol {
    @Id
    @Column(nullable = false)
    private String nombre;

    public Rol() {
    }
}
