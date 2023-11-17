package com.tpe.administracion.controllers;

import com.tpe.administracion.AuthorityConstant;
import com.tpe.administracion.models.clases.Monopatin;
import com.tpe.administracion.models.clases.Parada;
import com.tpe.administracion.models.entidades.Tarifa;
import com.tpe.administracion.services.AdministradorService;
import com.tpe.administracion.services.TarifaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("administradores")
public class AdministradorController {
    @Autowired
    private AdministradorService service;
    @Autowired
    private TarifaService tarifa_service;


    //ABM monopatines
    @PostMapping("/monopatines")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.ADMIN + "\" )")
    public ResponseEntity<?> saveMonopatin(@RequestHeader("Authorization") String token, @RequestBody Monopatin m) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(service.saveMonopatin(token, m));
    }


    @DeleteMapping("/monopatines/{id}")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.ADMIN + "\" )")
    public ResponseEntity<?> deleteMonopatin(@RequestHeader("Authorization") String token, @PathVariable Long id) throws Exception {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(service.deleteMonopatin(token, id));
    }

    @GetMapping("/monopatines/reporteMonopatinesOpVsMant")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.ADMIN + "\" )")
    public ResponseEntity<?> getReporteMonopatinesOpVsMant(@RequestHeader("Authorization") String token) throws Exception{
        return ResponseEntity.status(HttpStatus.OK).body(service.getReporteMonopatinesOpVsMant(token));
    }

    @GetMapping("/monopatines/anio/{anio}/cantViajesMayorA/{cant_viajes}")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.ADMIN + "\" )")
    public ResponseEntity<?> getMonopatinesConMasCantViajeEnAnio(@RequestHeader("Authorization") String token, @PathVariable int anio, @PathVariable Long cant_viajes){
        return ResponseEntity.status(HttpStatus.OK).body(service.getMonopatinesConMasCantViajeEnAnio(token, anio, cant_viajes));
    }

    @GetMapping("/monopatines/conKmsEntre/min/{minKms}/max/{maxKms}")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.ADMIN + "\" )")
    public ResponseEntity<?> getMonopatinesConKmsMayorAyMenorA(@RequestHeader("Authorization") String token, @PathVariable double minKms,
                                                               @PathVariable double maxKms) throws Exception{
        return ResponseEntity.status(HttpStatus.OK).body(service.getMonopatinesConKmsMayorAyMenorA(token, minKms, maxKms));
    }


    //ABM paradas
    @PostMapping("/paradas")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.ADMIN + "\" )")
    public ResponseEntity<?> saveParada(@RequestHeader("Authorization") String token, @RequestBody Parada p) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(service.saveParada(token, p));
    }

    @DeleteMapping("/paradas/{id}")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.ADMIN + "\" )")
    public ResponseEntity<?> deleteParada(@RequestHeader("Authorization") String token, @PathVariable Long id) throws Exception {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(service.deleteParada(token, id));
    }

    //Anular cuenta momentaneamente
    @PutMapping("/cuentas/anular/{id}")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.ADMIN + "\" )")
    public ResponseEntity<?> anularCuentaMomentaneamente(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.anularCuentaMomentaneamente(token, id));
    }

    //agregar una nueva tarifa
    @PostMapping("/tarifas")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.ADMIN + "\" )")
    public ResponseEntity<?> agregarTarifa(@RequestBody Tarifa t) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(tarifa_service.save(t));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ERROR. Revise los campos y vuelva a intentar.");
        }
    }
    //Total facturado
    @GetMapping("/tarifas/totalFacturadoEnAnio/{anio}/entreMes/{mes1}/y/{mes2}")
    @PreAuthorize("hasAnyAuthority(\"" + AuthorityConstant.ADMIN + "\" )")
    public ResponseEntity<?> getFacturacionTotalEnAnioEntreMeses(@RequestHeader("Authorization") String token, @PathVariable int anio, @PathVariable int mes1,
                                                                @PathVariable int mes2){
        return ResponseEntity.status(HttpStatus.OK).body(tarifa_service.getFacturacionTotalEnAnioEntreMeses(token, anio,mes1,mes2));
    }
}
