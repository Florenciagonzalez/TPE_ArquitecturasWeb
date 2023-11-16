package com.tpe.monopatines.modelos;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Data
public class Viaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_monopatin")
    private Monopatin monopatin;
    @Column(nullable = false)
    private Long id_usuario;


    @Column(nullable = false)
    private LocalDateTime fecha_hora_inicio;


    @Column(nullable = false)
    private LocalDateTime fecha_hora_fin;
    @Column(nullable = false)
    private double kilometros;
    @Column(nullable = false)
    private Long tiempo_pausa;

    @Column
    private LocalDateTime inicio_pausa;
    @Column(nullable = false)
    private boolean en_pausa;

    public Viaje() {
    }

    public Viaje(Monopatin monopatin, Long id_usuario, LocalDateTime fecha_hora_inicio, LocalDateTime fecha_hora_fin,
                 double kilometros, Long tiempo_pausa, LocalDateTime inicio_pausa, boolean en_pausa) {
        this.monopatin = monopatin;
        this.id_usuario = id_usuario;
        this.fecha_hora_inicio = fecha_hora_inicio;
        this.fecha_hora_fin = fecha_hora_fin;
        this.kilometros = kilometros;
        this.tiempo_pausa = tiempo_pausa;
        this.inicio_pausa = inicio_pausa;
        this.en_pausa = en_pausa;
    }
}
