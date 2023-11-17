package com.tpe.monopatines.controllers;

import com.tpe.monopatines.AuthorityConstant;
import com.tpe.monopatines.modelos.dto.ViajeDTO;
import com.tpe.monopatines.services.ViajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("viajes")
public class ViajeController {
    @Autowired
    private ViajeService service;

    @PostMapping("")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.USER + "\" )")
    public ResponseEntity<?> save(@RequestBody ViajeDTO v){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.save(v));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ERROR. Revise los campos y vuelva a intentar.");
        }
    }

    @GetMapping("/totalViajesEn/anio/{anio}/entreMes/{mes1}/y/{mes2}")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.ADMIN + "\" )")
    public ResponseEntity<?> getTotalViajesEnAnioEntreMeses(@PathVariable int anio, @PathVariable int mes1, @PathVariable int mes2){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.getTotalViajesEnAnioEntreMeses(anio, mes1, mes2));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR. No se encontraron viajes en el año " + anio +
                    "entre los meses solicitados");
        }
    }
}
