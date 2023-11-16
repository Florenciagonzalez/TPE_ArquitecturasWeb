package com.tpe.administracion.repository;

import com.tpe.administracion.models.entidades.Tarifa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface TarifaRepository extends JpaRepository<Tarifa, Long> {

    @Query("SELECT t FROM Tarifa t WHERE t.fecha_vigencia <= :fecha ORDER BY t.fecha_vigencia DESC LIMIT 1")
    Tarifa getTarifaEnFecha(LocalDate fecha);

}
