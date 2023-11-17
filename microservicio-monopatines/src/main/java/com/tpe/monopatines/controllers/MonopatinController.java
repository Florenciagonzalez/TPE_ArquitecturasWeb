package com.tpe.monopatines.controllers;

import com.tpe.monopatines.AuthorityConstant;
import com.tpe.monopatines.modelos.Monopatin;
import com.tpe.monopatines.services.MonopatinService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Monopatin", description = "Monopatin API")
@RestController
@RequestMapping("monopatines")

public class MonopatinController {
    @Autowired
    private MonopatinService service;

    //metodos accesibles para el admin
    
    @Operation(summary = "Guarda un monopatin.", description = "Guarda un monopatin")
    @PostMapping("/admin")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.ADMIN + "\" )")
    public ResponseEntity<?> save(@RequestBody Monopatin m) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.save(m));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error. No se pudo ingresar, revise los campos e intente nuevamente.");
        }
    }

    @PutMapping("/admin/{id}")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.ADMIN + "\" ) or hasAnyAuthority(\"" + AuthorityConstant.MAINTENANCE + "\" )")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Monopatin m) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.update(id, m));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error. No se pudo actualizar, revise los campos e intente nuevamente.");
        }
    }

    @DeleteMapping("/admin/{id}")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.ADMIN + "\" )")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try{
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(service.delete(id));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error. El monopatín que intenta eliminar no existe.");
        }
    }

    @GetMapping("/admin/{id}")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.ADMIN + "\" )")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.getById(id));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error. Por favor intente más tarde.");
        }
    }

    @GetMapping("/admin/cantEnMantVsOp")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.ADMIN + "\" )")
    public ResponseEntity<?> getReporteCantMantVsOp(){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.getReporteOpVsMant());
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR. Intente mas tarde");
        }
    }

    @GetMapping("/admin/anio/{anio}/cantViajesMayorA/{cant_viajes}")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.ADMIN + "\" )")
    public ResponseEntity<?> getConMasCantViajeEnAnio(@PathVariable int anio, @PathVariable Long cant_viajes){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.getConMasCantViajeEnAnio(anio,cant_viajes));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR. No se encontraron monopatines con mas viajes que lo solicitado");
        }
    }

    @GetMapping("/admin/conKmsEntre/min/{minKms}/max/{maxKms}")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.ADMIN + "\" )")
    public ResponseEntity<?> getConKmsEntre(@PathVariable double minKms, @PathVariable double maxKms){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.getConKmsEntre(minKms, maxKms));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR. No se encontraron monopatines entre los kms que los ingresados");
        }
    }

    //metodos accesibles para mantenimiento

    @GetMapping("/admin/reportesConTiempoPausa")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.MAINTENANCE + "\" )")
    public ResponseEntity<?> getReportesConTiempoPausa(){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.getReportesConTiempoPausa());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR. No se encontraron monopatines con tiempo de pausa");
        }
    }

    @GetMapping("/admin/reportesSinTiempoPausa")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.MAINTENANCE + "\" )")
    public ResponseEntity<?> getReportesSinTiempoPausa(){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.getReportesSinTiempoPausa());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR. No se encontraron monopatines sin tiempo de pausa");
        }
    }


    //metodos accesibles para usuarios

    @GetMapping("/usuario/disponiblesEnZona/latitud/{latitud}/longitud/{longitud}")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.USER + "\" )")
    public ResponseEntity<?> getMonopatinesDisponiblesEnZona(@PathVariable double latitud, @PathVariable double longitud) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.getMonopatinesDisponiblesEnZona(latitud, longitud));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR. No se encontraron monopatines disponibles en su zona");
        }
    }

}
