package com.tpe.paradas.controller;


import com.tpe.paradas.AuthorityConstant;
import com.tpe.paradas.modelos.dto.ParadaDTO;
import com.tpe.paradas.modelos.Parada;
import com.tpe.paradas.service.ParadaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("paradas")
public class ParadaController {
    @Autowired
    private ParadaService service;

    @PostMapping("")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.ADMIN + "\" )")
    public ResponseEntity<?> save(@RequestBody Parada p) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.save(p));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error. No se pudo ingresar, revise los campos e intente nuevamente.");
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.ADMIN + "\" )")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ParadaDTO p) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.update(id, p));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error. No se pudo actualizar, revise los campos e intente nuevamente.");
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.ADMIN + "\" )")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try{
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(service.delete(id));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error. La parada que intenta eliminar no existe.");
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.ADMIN + "\" )")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.getById(id));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error. Por favor intente más tarde.");
        }
    }
}
