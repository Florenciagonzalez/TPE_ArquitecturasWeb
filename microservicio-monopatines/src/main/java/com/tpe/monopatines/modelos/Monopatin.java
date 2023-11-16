package com.tpe.monopatines.modelos;


import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity(name = "monopatines")
@Table(name = "monopatines")
public class Monopatin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String estado;
    @Column(nullable = false)
    private double kilometros_recorridos;

    @Column(nullable = false)
    private Long id_gps;
    @Column(nullable = false)
    private double latitud;
    @Column(nullable = false)
    private double longitud;

    public Monopatin() {
    }

    public Monopatin(String estado, double kilometros_recorridos, Long id_gps, double latitud, double longitud) {
        this.estado = estado;
        this.kilometros_recorridos = kilometros_recorridos;
        this.id_gps = id_gps;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public Long getId() {
        return id;
    }

    public String getEstado() {
        return estado;
    }

    public double getKilometros_recorridos() {
        return kilometros_recorridos;
    }

    public Long getId_gps() {
        return id_gps;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setKilometros_recorridos(double kilometros_recorridos) {
        this.kilometros_recorridos = kilometros_recorridos;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
}
