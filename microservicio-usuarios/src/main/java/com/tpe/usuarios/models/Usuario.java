package com.tpe.usuarios.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private Long nro_celular;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String apellido;

    @ManyToOne
    @JoinColumn(name = "id_cuenta")
    private Cuenta id_cuenta;

    @ManyToOne
    @JoinColumn(name = "rol")
    private Rol rol;
    @Column
    private String password;

    public Usuario() {
    }

    public Usuario(String email, Long nro_celular, String nombre, String apellido, Cuenta id_cuenta, Rol rol, String password) {
        this.email = email;
        this.nro_celular = nro_celular;
        this.nombre = nombre;
        this.apellido = apellido;
        this.id_cuenta = id_cuenta;
        this.rol = rol;
        this.password = password;
    }

}
