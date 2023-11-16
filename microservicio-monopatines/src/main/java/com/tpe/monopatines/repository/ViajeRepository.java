package com.tpe.monopatines.repository;

import com.tpe.monopatines.modelos.dto.ViajeDTO;
import com.tpe.monopatines.modelos.Viaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ViajeRepository extends JpaRepository<Viaje, Long> {

    @Query("SELECT v FROM Viaje v WHERE " +
            "YEAR(v.fecha_hora_inicio) = :anio AND MONTH(v.fecha_hora_inicio) BETWEEN :mes1 AND :mes2")
    List<ViajeDTO> getTotalViajesEnAnioEntreMeses(int anio, int mes1, int mes2);
}
