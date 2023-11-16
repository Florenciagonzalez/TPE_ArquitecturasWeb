package com.tpe.mantenimiento.repository;

import com.tpe.mantenimiento.modelos.Mantenimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MantenimientoRepository extends JpaRepository<Mantenimiento, Long> {

    @Query("SELECT m FROM Mantenimiento m WHERE m.id_monopatin = :id_monopatin AND m.esta_reparado = false")
    Mantenimiento getMantActivoByMonopatinId(Long id_monopatin);
}
