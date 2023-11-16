package com.tpe.monopatines.modelos.dto;


import com.tpe.monopatines.modelos.Monopatin;
import lombok.Data;

import java.io.Serializable;

@Data
public class MonopatinDTO implements Serializable {
    private Long id;
    private String estado;
    private double kilometros_recorridos;
    private Long id_gps;
    private double latitud;
    private double longitud;

    public MonopatinDTO() {
    }

    public MonopatinDTO(Monopatin m) {
        this.id = m.getId();
        this.estado = m.getEstado();
        this.kilometros_recorridos = m.getKilometros_recorridos();
        this.id_gps = m.getId_gps();
        this.latitud = m.getLatitud();
        this.longitud = m.getLongitud();
    }

    public Long getId() {
        return id;
    }

    public String getEstado() {
        return estado;
    }

    public double getKilometros_recorridos() {
        return kilometros_recorridos;
    }

    public Long getId_gps() {
        return id_gps;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }
}
