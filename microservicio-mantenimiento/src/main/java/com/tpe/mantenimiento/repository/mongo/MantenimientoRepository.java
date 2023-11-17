package com.tpe.mantenimiento.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.tpe.mantenimiento.modelos.Mantenimiento;

public interface MantenimientoRepository extends MongoRepository<Mantenimiento, Long> {
	@Query("{'id_monopatin': ?0, 'esta_reparado': ?1}")
	 public Mantenimiento findByIdMonopatinAndEstaReparado(Long id_monopatin, boolean esta_reparado);
}
