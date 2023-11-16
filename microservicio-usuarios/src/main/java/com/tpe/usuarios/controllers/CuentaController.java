package com.tpe.usuarios.controllers;

import com.tpe.usuarios.AuthorityConstant;
import com.tpe.usuarios.models.dto.CuentaDTO;
import com.tpe.usuarios.models.Cuenta;
import com.tpe.usuarios.services.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("cuentas")
public class CuentaController {
    @Autowired
    private CuentaService service;

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody Cuenta c) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.save(c));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ERROR. revise los campos e intente nuevamente.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody CuentaDTO c) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.update(id, c));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ERROR. No se ha podido actualizar la cuenta que desea.");
        }
    }

    @PutMapping("/anular/{id}")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.ADMIN + "\" )")
    public ResponseEntity<?> anularCuenta(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.anularCuenta(id));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ERROR. No se ha podido anular la cuenta que desea.");
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.ADMIN + "\" )")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.getById(id));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR. La cuenta que está buscando, no fue encontrada");
        }
    }
}
