package com.tpe.administracion.models.entidades;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
@Data
@Entity
public class Tarifa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String clave;
    @Column
    private Double valor;
    @Column
    private Double valor_pausaExtendida;
    @Column
    private LocalDate fecha_vigencia;

    public Tarifa() {
    }

    public Tarifa(String clave, Double valor, Double valor_pausaExtendida, LocalDate fecha_vigencia) {
        this.clave = clave;
        this.valor = valor;
        this.valor_pausaExtendida = valor_pausaExtendida;
        this.fecha_vigencia = fecha_vigencia;
    }

    public Long getId() {
        return id;
    }

    public String getClave() {
        return clave;
    }

    public Double getValor() {
        return valor;
    }

    public Double getValor_pausaExtendida() {
        return valor_pausaExtendida;
    }

    public LocalDate getFecha_vigencia() {
        return fecha_vigencia;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public void setValor_pausaExtendida(Double valor_pausaExtendida) {
        this.valor_pausaExtendida = valor_pausaExtendida;
    }

    public void setFecha_vigencia(LocalDate fecha_vigencia) {
        this.fecha_vigencia = fecha_vigencia;
    }
}
