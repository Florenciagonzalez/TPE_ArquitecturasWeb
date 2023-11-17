package com.tpe.mantenimiento.controller;

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
	
	
	@PostMapping("")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.MAINTENANCE + "\" )")
    public ResponseEntity<?> save(@RequestBody Mantenimiento m) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(mantenimientoService.save(m));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error. No se pudo ingresar, revise los campos e intente nuevamente.");
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.MAINTENANCE + "\" )")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody MantenimientoDTO m) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(mantenimientoService.update(id, m));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error. No se pudo actualizar, revise los campos e intente nuevamente.");
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.MAINTENANCE + "\" )")
    public ResponseEntity<?> delete(@PathVariable String id) {
        try{
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(mantenimientoService.delete(id));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error. El mantenimiento que intenta eliminar no existe.");
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.MAINTENANCE + "\" )")
    public ResponseEntity<?> getById(@PathVariable String id) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(mantenimientoService.getById(id));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error. Por favor intente más tarde.");
        }
    }

    @GetMapping("/activoPorIdMonopatin/{id_monopatin}")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.MAINTENANCE + "\" )")
    public ResponseEntity<?> getById_monopatin(@PathVariable Long id_monopatin) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(mantenimientoService.getMantActivoByMonopatinId(id_monopatin));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error. Por favor intente más tarde.");
        }
    }

    @PutMapping("/monopatines/enviarAMantenimiento/{id}")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.MAINTENANCE + "\" )")
    public ResponseEntity<?> enviarMonopatinMantenimiento(@RequestHeader("Authorization") String token, @PathVariable Long id)  {
        return ResponseEntity.status(HttpStatus.OK).body(mantenimientoService.enviarMonopatinMantenimiento(token, id));
    }

    @PutMapping("/monopatines/sacarDeMantenimiento/{id}")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.MAINTENANCE + "\" )")
    public ResponseEntity<?> sacarMonopatinDeMantenimiento(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(mantenimientoService.sacarMonopatinDeMantenimiento(token, id));
    }

    @GetMapping("/monopatines/reportesConTiempoPausa")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.MAINTENANCE + "\" )")
    public ResponseEntity<?> getReportesMonopatinesConTiempoPausa(@RequestHeader("Authorization") String token) throws Exception{
        return ResponseEntity.status(HttpStatus.OK).body(mantenimientoService.getReportesMonopatinesConTiempoPausa(token));
    }

    @GetMapping("/monopatines/reportesSinTiempoPausa")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.MAINTENANCE + "\" )")
    public ResponseEntity<?> getReportesMonopatinesSinTiempoPausa(@RequestHeader("Authorization") String token) throws Exception{
        return ResponseEntity.status(HttpStatus.OK).body(mantenimientoService.getReportesMonopatinesSinTiempoPausa(token));
    }
}
