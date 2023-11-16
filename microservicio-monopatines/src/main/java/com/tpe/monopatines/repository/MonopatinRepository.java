package com.tpe.monopatines.repository;

import com.tpe.monopatines.modelos.dto.MonopatinCantViajesDTO;
import com.tpe.monopatines.modelos.dto.MonopatinDTO;
import com.tpe.monopatines.modelos.dto.ReportePorTiempo;
import com.tpe.monopatines.modelos.Monopatin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonopatinRepository extends JpaRepository<Monopatin, Long> {

   @Query("SELECT m FROM monopatines m WHERE m.estado ='disponible' AND (m.latitud BETWEEN :latitudMin AND :latitudMax)" +
           " AND (m.longitud BETWEEN :longitudMin AND :longitudMax)")
   List<MonopatinDTO> getMonopatinesDisponiblesEnZona(double latitudMin, double latitudMax, double longitudMin, double longitudMax);

   @Query("SELECT COUNT(m) FROM monopatines m WHERE m.estado = :estado")
    int getCantEnEstado(String estado);

   @Query("SELECT new com.tpe.monopatines.modelos.dto.MonopatinCantViajesDTO(m.id, COUNT(v), YEAR(v.fecha_hora_inicio)) FROM Viaje v JOIN v.monopatin m " +
           "WHERE YEAR(v.fecha_hora_inicio) = :anio GROUP BY m.id " +
           "HAVING COUNT(v) > :cant_viajes")
    List<MonopatinCantViajesDTO> getConMasCantViajeEnAnio(int anio, Long cant_viajes);

   @Query("SELECT new com.tpe.monopatines.modelos.dto.ReportePorTiempo(v.monopatin.id, SUM(v.kilometros), SUM(v.tiempo_pausa)) " +
                "FROM Viaje v WHERE v.tiempo_pausa > 0 GROUP BY v.monopatin.id")
    List<ReportePorTiempo> getReportesConTiempoPausa();

    @Query("SELECT new com.tpe.monopatines.modelos.dto.ReportePorTiempo(v.monopatin.id, SUM(v.kilometros), v.tiempo_pausa) " +
            "FROM Viaje v WHERE v.tiempo_pausa = 0 GROUP BY v.monopatin.id")
    List<ReportePorTiempo> getReportesSinTiempoPausa();

    @Query("SELECT m FROM monopatines m WHERE m.kilometros_recorridos BETWEEN :minKms AND :maxKms ORDER BY m.kilometros_recorridos DESC")
    List<MonopatinDTO> getConKmsEntre(double minKms, double maxKms);
}
