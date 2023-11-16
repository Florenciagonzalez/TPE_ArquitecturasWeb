package com.tpe.mantenimiento.modelos;

import jakarta.persistence.*;
import lombok.Data;


import java.time.LocalDateTime;

@Entity
@Data
public class Mantenimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long id_monopatin;
    @Column(nullable = false)
    private LocalDateTime fecha_inicio;
    @Column
    private LocalDateTime fecha_fin;
    @Column(nullable = false)
    private boolean esta_reparado;

    public Mantenimiento() {
    }

    public Mantenimiento(Long id_monopatin, LocalDateTime fecha_inicio, LocalDateTime fecha_fin, boolean esta_reparado) {
        this.id_monopatin = id_monopatin;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.esta_reparado = esta_reparado;
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

    public void setId_monopatin(Long id_monopatin) {
        this.id_monopatin = id_monopatin;
    }

    public void setFecha_inicio(LocalDateTime fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public void setFecha_fin(LocalDateTime fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public void setEsta_reparado(boolean esta_reparado) {
        this.esta_reparado = esta_reparado;
    }
}
