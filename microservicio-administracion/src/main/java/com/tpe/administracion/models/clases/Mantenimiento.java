package com.tpe.administracion.models.clases;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Mantenimiento implements Serializable {
    private Long id;
    private Long id_monopatin;
    private LocalDateTime fecha_inicio;
    private LocalDateTime fecha_fin;
    private boolean esta_reparado;

    public Mantenimiento() {
    }

    public Mantenimiento(Mantenimiento m) {
        this.id = m.getId();
        this.id_monopatin = m.getId_monopatin();
        this.fecha_inicio = m.getFecha_inicio();
        this.fecha_fin = m.getFecha_fin();
        this.esta_reparado = m.isEsta_reparado();
    }
}
