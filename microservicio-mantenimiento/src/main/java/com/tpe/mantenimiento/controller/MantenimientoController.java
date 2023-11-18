package com.tpe.mantenimiento.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.tpe.mantenimiento.AuthorityConstant;
import com.tpe.mantenimiento.modelos.Mantenimiento;
import com.tpe.mantenimiento.modelos.dto.MantenimientoDTO;
import com.tpe.mantenimiento.service.MantenimientoService;

@RestController
@RequestMapping("mantenimiento")
public class MantenimientoController {
	
	@Autowired
	private MantenimientoService mantenimientoService;


    @Operation(summary = "Guardar mantenimiento", description = "Crea un nuevo mantenimiento.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED"),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN")
    })
    @PostMapping("")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.MAINTENANCE + "\" )")
    public ResponseEntity<?> save(@RequestBody Mantenimiento m) {
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(mantenimientoService.save(m));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error. No se pudo ingresar, revise los campos e intente nuevamente.");
        }
    }

    @Operation(summary = "Actualizar mantenimiento", description = "Actualiza un mantenimiento segun su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED"),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.MAINTENANCE + "\" )")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody MantenimientoDTO m) {
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(mantenimientoService.update(id, m));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error. No se pudo actualizar, revise los campos e intente nuevamente.");
        }
    }

    @Operation(summary = "Eliminar mantenimiento", description = "Elimina un mantenimiento segun su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "NO CONTENT"),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED"),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.MAINTENANCE + "\" )")
    public ResponseEntity<?> delete(@PathVariable String id) {
        try{
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(mantenimientoService.delete(id));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error. El mantenimiento que intenta eliminar no existe.");
        }
    }

    @Operation(summary = "Obtener mantenimiento", description = "Obtiene un mantenimiento segun su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED"),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.MAINTENANCE + "\" )")
    public ResponseEntity<?> getById(@PathVariable String id) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(mantenimientoService.getById(id));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error. Por favor intente más tarde.");
        }
    }

    @Operation(summary = "Obtener mantenimiento activo segun ID del monopatin",
            description = "Obtiene un mantenimiento activo segun el ID del monopatin.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED"),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND")
    })
    @GetMapping("/activoPorIdMonopatin/{id_monopatin}")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.MAINTENANCE + "\" )")
    public ResponseEntity<?> getById_monopatin(@PathVariable Long id_monopatin) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(mantenimientoService.getMantActivoByMonopatinId(id_monopatin));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error. Por favor intente más tarde.");
        }
    }

    @Operation(summary = "Crea un nuevo mantenimiento dado el ID del monopatin",
            description = "Crea un mantenimiento segun dado el ID de un monopatin y actualiza el estado del mismo a 'en mantenimiento'.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED"),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND")
    })
    @PutMapping("/monopatines/enviarAMantenimiento/{id}")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.MAINTENANCE + "\" )")
    public ResponseEntity<?> enviarMonopatinMantenimiento(@RequestHeader("Authorization") String token, @PathVariable Long id)  {
        return ResponseEntity.status(HttpStatus.OK).body(mantenimientoService.enviarMonopatinMantenimiento(token, id));
    }

    @Operation(summary = "Termina un mantenimiento dado el ID del monopatin",
            description = "Setea el estado de un monopatin en mantenimiento a 'disponible' y agrega la fecha de finalizacion del mantenimiento.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED"),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND")
    })
    @PutMapping("/monopatines/sacarDeMantenimiento/{id}")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.MAINTENANCE + "\" )")
    public ResponseEntity<?> sacarMonopatinDeMantenimiento(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(mantenimientoService.sacarMonopatinDeMantenimiento(token, id));
    }

    @Operation(summary = "Listado de reportes de kms de monopatines con tiempo de pausa",
            description = "Devuelve un listado de reportes de los kms de cada monopatin con su tiempo de pausa e ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED"),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND")
    })
    @GetMapping("/monopatines/reportesConTiempoPausa")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.MAINTENANCE + "\" )")
    public ResponseEntity<?> getReportesMonopatinesConTiempoPausa(@RequestHeader("Authorization") String token) throws Exception{
        return ResponseEntity.status(HttpStatus.OK).body(mantenimientoService.getReportesMonopatinesConTiempoPausa(token));
    }

    @Operation(summary = "Listado de reportes de kms de monopatines sin tiempo de pausa",
            description = "Devuelve un listado de reportes de los kms de cada monopatin que no tiene tiempo de pausa y su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED"),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND")
    })
    @GetMapping("/monopatines/reportesSinTiempoPausa")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.MAINTENANCE + "\" )")
    public ResponseEntity<?> getReportesMonopatinesSinTiempoPausa(@RequestHeader("Authorization") String token) throws Exception{
        return ResponseEntity.status(HttpStatus.OK).body(mantenimientoService.getReportesMonopatinesSinTiempoPausa(token));
    }
}
