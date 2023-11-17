package com.tpe.mantenimiento.modelos;

import lombok.Data;

import java.io.Serializable;

@Data
public class Monopatin implements Serializable {
    private Long id;
    private double kilometros_recorridos;
    private double latitud;
    private double longitud;
    private Long id_gps;
    private String estado;

    public Monopatin() {
    }

    public Monopatin(Monopatin m) {
        this.id = m.getId();
        this.kilometros_recorridos = m.getKilometros_recorridos();
        this.latitud = m.getLatitud();
        this.longitud = m.getLongitud();
        this.id_gps = m.getId_gps();
        this.estado = m.getEstado();
    }
}
