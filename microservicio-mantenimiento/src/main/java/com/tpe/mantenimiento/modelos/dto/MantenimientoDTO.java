package com.tpe.mantenimiento.modelos.dto;

import com.tpe.mantenimiento.modelos.Mantenimiento;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class MantenimientoDTO implements Serializable {
    private String id;
    private Long id_monopatin;
    private LocalDateTime fecha_inicio;
    private LocalDateTime fecha_fin;
    private boolean esta_reparado;

    public MantenimientoDTO() {
    }

    public MantenimientoDTO(Mantenimiento m) {
        this.id = m.getId();
        this.id_monopatin = m.getId_monopatin();
        this.fecha_inicio = m.getFecha_inicio();
        this.fecha_fin = m.getFecha_fin();
        this.esta_reparado = m.isEsta_reparado();
    }
}
