package com.tpe.monopatines.controllers;

import com.tpe.monopatines.AuthorityConstant;
import com.tpe.monopatines.modelos.dto.ViajeDTO;
import com.tpe.monopatines.services.ViajeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Guardar un viaje.", description = "Agrega un nuevo viaje.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED"),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN")
    })
    @PostMapping("")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.USER + "\" )")
    public ResponseEntity<?> save(@RequestBody ViajeDTO v){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.save(v));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ERROR. Revise los campos y vuelva a intentar.");
        }
    }

    @Operation(summary = "Listado de viajes en un periodo de tiempo dado",
            description = "Obtiene un listado de viajes entre dos meses dados en el año especificado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED"),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND")
    })
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
