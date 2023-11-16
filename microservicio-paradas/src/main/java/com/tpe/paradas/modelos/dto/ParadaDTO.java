package com.tpe.paradas.modelos.dto;

import com.tpe.paradas.modelos.Parada;
import lombok.Data;

import java.io.Serializable;
@Data
public class ParadaDTO implements Serializable {
    private Long id;
    private Double latitud;
    private Double longitud;

    public ParadaDTO() {
    }

    public ParadaDTO(Parada p) {
        this.id = p.getId();
        this.latitud = p.getLatitud();
        this.longitud = p.getLongitud();
    }
}
