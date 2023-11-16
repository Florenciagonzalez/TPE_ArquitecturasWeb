package com.tpe.usuarios.models;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Entity( name = "cuentas")
@Data
@Table(name = "cuentas")
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private LocalDate fecha_alta;
    @Column(nullable = false)
    private Float saldo;
    @Column(nullable = false)
    private String estado;

    @OneToMany(mappedBy = "id_cuenta", cascade = CascadeType.MERGE)
    private List<Usuario> usuarios;

    public Cuenta() {
    }

    public Cuenta(LocalDate fecha_alta, Float saldo, String estado) {
        this.fecha_alta = fecha_alta;
        this.saldo = saldo;
        this.estado = estado;
    }

}


