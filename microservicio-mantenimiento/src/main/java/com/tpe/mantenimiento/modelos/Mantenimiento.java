package com.tpe.mantenimiento.modelos;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "coleccion_mantenimiento")
public class Mantenimiento {
	@Id
	private Long id;
	private Long id_monopatin;
	private LocalDateTime fecha_inicio;
	private LocalDateTime fecha_fin;
	private boolean esta_reparado;
	
}
