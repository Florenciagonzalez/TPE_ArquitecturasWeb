package com.tpe.monopatines.modelos.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReportePorTiempo implements Serializable {
    private Long id;
    private double kilometros_recorridos;
    private Long tiempo_pausa;

    public ReportePorTiempo() {
    }

    public ReportePorTiempo(Long id, double kilometros_recorridos, Long tiempo_pausa) {
        this.id = id;
        this.kilometros_recorridos = kilometros_recorridos;
        this.tiempo_pausa = tiempo_pausa;
    }

}
