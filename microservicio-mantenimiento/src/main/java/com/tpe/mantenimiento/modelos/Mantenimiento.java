package com.tpe.mantenimiento.modelos;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document(collection = "coleccion_mantenimiento")
public class Mantenimiento {
	@Id
	private String id;
	private Long id_monopatin;
	private LocalDateTime fecha_inicio;
	private LocalDateTime fecha_fin;
	
	private boolean esta_reparado;
	public Mantenimiento(Long id_monopatin, LocalDateTime fecha_inicio, LocalDateTime fecha_fin, boolean esta_reparado) {
		this.id_monopatin = id_monopatin;
		this.fecha_inicio = fecha_inicio;
		this.fecha_fin = fecha_fin;
		this.esta_reparado = esta_reparado;
	}
	
	
	
}
