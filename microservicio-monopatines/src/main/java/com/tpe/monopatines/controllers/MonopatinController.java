package com.tpe.monopatines.controllers;

import com.tpe.monopatines.AuthorityConstant;
import com.tpe.monopatines.modelos.Monopatin;
import com.tpe.monopatines.services.MonopatinService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    
    @Operation(summary = "Guarda un monopatin.", description = "Agrega un nuevo monopatin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED"),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN")
    })
    @PostMapping("")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.ADMIN + "\" )")
    public ResponseEntity<?> save(@RequestBody Monopatin m) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.save(m));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error. No se pudo ingresar, revise los campos e intente nuevamente.");
        }
    }

    @Operation(summary = "Actualizar monopatin", description = "Actualiza un monopatin segun su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED"),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.ADMIN + "\" ) or hasAnyAuthority(\"" + AuthorityConstant.MAINTENANCE + "\" )")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Monopatin m) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.update(id, m));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error. No se pudo actualizar, revise los campos e intente nuevamente.");
        }
    }

    @Operation(summary = "Eliminar un monopatin.", description = "Elimina un monopatin segun su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "NO CONTENT"),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED"),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.ADMIN + "\" )")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try{
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(service.delete(id));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error. El monopatín que intenta eliminar no existe.");
        }
    }

    @Operation(summary = "Obtener un monopatin.", description = "Obtiene un monopatin segun su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED"),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.ADMIN + "\" ) or hasAnyAuthority(\"" + AuthorityConstant.MAINTENANCE + "\" )")

    public ResponseEntity<?> getById(@PathVariable Long id) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.getById(id));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error. Por favor intente más tarde.");
        }
    }

    @Operation(summary = "Cantidad de monopatines en mantenimiento vs en operacion",
            description = "Obtiene la cantidad de monopatines en mantenimiento vs en operacion.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED"),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND")
    })
    @GetMapping("/cantEnMantVsOp")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.ADMIN + "\" )")
    public ResponseEntity<?> getReporteCantMantVsOp(){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.getReporteOpVsMant());
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR. Intente mas tarde");
        }
    }

    @Operation(summary = "Listado de monopatines con la cantidad de viajes en un año dado.",
            description = "Obtiene los monopatines de un año dado que tengan una cantidad de viajes mayor a los dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED"),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND")
    })
    @GetMapping("/anio/{anio}/cantViajesMayorA/{cant_viajes}")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.ADMIN + "\" )")
    public ResponseEntity<?> getConMasCantViajeEnAnio(@PathVariable int anio, @PathVariable Long cant_viajes){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.getConMasCantViajeEnAnio(anio,cant_viajes));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR. No se encontraron monopatines con mas viajes que lo solicitado");
        }
    }

    @Operation(summary = "Listado de monopatines con kms entre un minimo y maximo dado",
            description = "Obtiene un listado de monopatines con una cantidad de kms entre un minimo y maximo dado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED"),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND")
    })
    @GetMapping("/conKmsEntre/min/{minKms}/max/{maxKms}")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.ADMIN + "\" )")
    public ResponseEntity<?> getConKmsEntre(@PathVariable double minKms, @PathVariable double maxKms){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.getConKmsEntre(minKms, maxKms));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR. No se encontraron monopatines entre los kms que los ingresados");
        }
    }

    //metodos accesibles para mantenimiento

    @Operation(summary = "Listado de reportes de kms de monopatines con tiempo de pausa",
            description = "Devuelve un listado de reportes de los kms de cada monopatin con su tiempo de pausa e ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED"),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND")
    })
    @GetMapping("/reportesConTiempoPausa")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.MAINTENANCE + "\" )")
    public ResponseEntity<?> getReportesConTiempoPausa(){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.getReportesConTiempoPausa());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR. No se encontraron monopatines con tiempo de pausa");
        }
    }

    @Operation(summary = "Listado de reportes de kms de monopatines sin tiempo de pausa",
            description = "Devuelve un listado de reportes de los kms de cada monopatin que no tiene tiempo de pausa y su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED"),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND")
    })
    @GetMapping("/reportesSinTiempoPausa")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.MAINTENANCE + "\" )")
    public ResponseEntity<?> getReportesSinTiempoPausa(){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.getReportesSinTiempoPausa());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR. No se encontraron monopatines sin tiempo de pausa");
        }
    }


    //metodos accesibles para usuarios

    @Operation(summary = "Listado de monopatines disponibles en una zona dada",
            description = "Devuelve un listado de monopatines cercanos a una latitud y longitud dada.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED"),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND")
    })
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
