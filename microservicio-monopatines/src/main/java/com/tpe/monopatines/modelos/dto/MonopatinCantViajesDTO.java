package com.tpe.monopatines.modelos.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class MonopatinCantViajesDTO implements Serializable {
    private Long id;
    private Long cant_viajes;
    private int anio;

    public MonopatinCantViajesDTO() {
    }

    public MonopatinCantViajesDTO(Long id, Long cant_viajes, int anio) {
        this.id = id;
        this.cant_viajes = cant_viajes;
        this.anio = anio;
    }
}
