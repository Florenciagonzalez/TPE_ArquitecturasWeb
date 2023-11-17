package com.tpe.administracion.models.clases;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Viaje implements Serializable {
    private Long id;
    private Long id_monopatin;
    private Long id_usuario;
    private LocalDateTime fecha_hora_inicio;
    private LocalDateTime fecha_hora_fin;
    private double kilometros;
    private Long tiempo_pausa;
    private LocalDateTime inicio_pausa;
    private boolean en_pausa;

    public Viaje() {
    }

    public Viaje(Viaje v) {
        this.id = v.getId();
        this.id_monopatin = v.getId_monopatin();
        this.id_usuario = v.getId_usuario();
        this.fecha_hora_inicio = v.getFecha_hora_inicio();
        this.fecha_hora_fin = v.getFecha_hora_fin();
        this.kilometros = v.getKilometros();
        this.tiempo_pausa = v.getTiempo_pausa();
        this.inicio_pausa = v.getInicio_pausa();
        this.en_pausa = v.isEn_pausa();
    }

}
