package com.tpe.administracion.controllers;

import com.tpe.administracion.models.entidades.Tarifa;
import com.tpe.administracion.services.TarifaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("tarifas")
public class TarifaController {
    @Autowired
    private TarifaService service;

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody Tarifa t) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.save(t));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error. No se pudo ingresar, revise los campos e intente nuevamente.");
        }
    }

    @PutMapping("/tarifaExtra/{id}")
    public ResponseEntity<?> definirTarifaExtra(@PathVariable Long id, @RequestBody Double tarifa_extra) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.definirTarifaExtra(id, tarifa_extra));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error. No se pudo actualizar, revise los campos e intente nuevamente.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.getById(id));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error. No se encontró la tarifa que está buscando.");

        }
    }

}
