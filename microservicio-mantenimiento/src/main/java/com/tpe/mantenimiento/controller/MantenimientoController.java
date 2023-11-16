package com.tpe.mantenimiento.controller;

import com.tpe.mantenimiento.AuthorityConstant;
import com.tpe.mantenimiento.modelos.dto.MantenimientoDTO;
import com.tpe.mantenimiento.modelos.Mantenimiento;
import com.tpe.mantenimiento.service.MantenimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("mantenimiento")
public class MantenimientoController {
    @Autowired
    private MantenimientoService service;

    @PostMapping("")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.MAINTENANCE + "\" )")
    public ResponseEntity<?> save(@RequestBody Mantenimiento m) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.save(m));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error. No se pudo ingresar, revise los campos e intente nuevamente.");
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.MAINTENANCE + "\" )")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody MantenimientoDTO m) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.update(id, m));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error. No se pudo actualizar, revise los campos e intente nuevamente.");
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.MAINTENANCE + "\" )")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try{
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(service.delete(id));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error. El mantenimiento que intenta eliminar no existe.");
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.MAINTENANCE + "\" )")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.getById(id));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error. Por favor intente más tarde.");
        }
    }

    @GetMapping("/activoPorIdMonopatin/{id_monopatin}")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.MAINTENANCE + "\" )")
    public ResponseEntity<?> getById_monopatin(@PathVariable Long id_monopatin) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.getMantActivoByMonopatinId(id_monopatin));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error. Por favor intente más tarde.");
        }
    }
}
