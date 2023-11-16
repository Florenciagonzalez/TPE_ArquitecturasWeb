package com.tpe.administracion.models.dto;

import com.tpe.administracion.models.entidades.Tarifa;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class TarifaDTO implements Serializable {
    private Long id;
    private String clave;
    private Double valor;
    private Double valor_pausaExtendida;
    private LocalDate fecha_vigencia;

    public TarifaDTO() {
    }

    public TarifaDTO(Tarifa t) {
        this.id = t.getId();
        this.clave = t.getClave();
        this.valor = t.getValor();
        this.valor_pausaExtendida = t.getValor_pausaExtendida();
        this.fecha_vigencia = t.getFecha_vigencia();
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
}
