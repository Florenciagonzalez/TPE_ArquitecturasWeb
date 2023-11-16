package com.tpe.administracion.models.clases;

import lombok.Data;

import java.io.Serializable;

@Data
public class Parada implements Serializable {
    private Long id;
    private Double latitud;
    private Double longitud;

    public Parada() {
    }

    public Parada(Parada p) {
        this.id = p.getId();
        this.latitud = p.getLatitud();
        this.longitud = p.getLongitud();
    }
}
