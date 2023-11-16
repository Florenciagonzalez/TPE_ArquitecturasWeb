package com.tpe.administracion.models.clases;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class Cuenta implements Serializable {
    private Long id;
    private LocalDate fecha_alta;
    private Float saldo;
    private String estado;

    public Cuenta() {
    }

    public Cuenta(Cuenta c) {
        this.fecha_alta = c.getFecha_alta();
        this.saldo = c.getSaldo();
        this.estado = c.getEstado();
    }

    public Long getId() {
        return id;
    }

    public LocalDate getFecha_alta() {
        return fecha_alta;
    }

    public Float getSaldo() {
        return saldo;
    }

    public String getEstado() {
        return estado;
    }
}
