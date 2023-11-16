package com.tpe.mantenimiento.modelos.dto;

import com.tpe.mantenimiento.modelos.Mantenimiento;

import java.io.Serializable;
import java.time.LocalDateTime;

public class MantenimientoDTO implements Serializable {
    private Long id;
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

    public Long getId() {
        return id;
    }

    public Long getId_monopatin() {
        return id_monopatin;
    }

    public LocalDateTime getFecha_inicio() {
        return fecha_inicio;
    }

    public LocalDateTime getFecha_fin() {
        return fecha_fin;
    }

    public boolean isEsta_reparado() {
        return esta_reparado;
    }
}
