package com.tpe.administracion.controllers;

import com.tpe.administracion.models.clases.Monopatin;
import com.tpe.administracion.models.clases.Parada;
import com.tpe.administracion.models.entidades.Tarifa;
import com.tpe.administracion.services.AdministradorService;
import com.tpe.administracion.services.TarifaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> saveMonopatin(@RequestBody Monopatin m) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(service.saveMonopatin(m));
    }

    @PutMapping("/monopatines/enviarAMantenimiento/{id}")
    public ResponseEntity<?> enviarMonopatinMantenimiento(@PathVariable Long id)  {
        return ResponseEntity.status(HttpStatus.OK).body(service.enviarMonopatinMantenimiento(id));
    }

    @PutMapping("/monopatines/sacarDeMantenimiento/{id}")
    public ResponseEntity<?> sacarMonopatinDeMantenimiento(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.sacarMonopatinDeMantenimiento(id));
    }

    @DeleteMapping("/monopatines/{id}")
    public ResponseEntity<?> deleteMonopatin(@PathVariable Long id) throws Exception {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(service.deleteMonopatin(id));
    }

    @GetMapping("/monopatines/reporteMonopatinesOpVsMant")
    public ResponseEntity<?> getReporteMonopatinesOpVsMant() throws Exception{
        return ResponseEntity.status(HttpStatus.OK).body(service.getReporteMonopatinesOpVsMant());
    }

    @GetMapping("/monopatines/anio/{anio}/cantViajesMayorA/{cant_viajes}")
    public ResponseEntity<?> getMonopatinesConMasCantViajeEnAnio(@PathVariable int anio, @PathVariable Long cant_viajes){
        return ResponseEntity.status(HttpStatus.OK).body(service.getMonopatinesConMasCantViajeEnAnio(anio, cant_viajes));
    }

    @GetMapping("/monopatines/conKmsEntre/min/{minKms}/max/{maxKms}")
    public ResponseEntity<?> getMonopatinesConKmsMayorAyMenorA(@PathVariable double minKms,
                                                               @PathVariable double maxKms) throws Exception{
        return ResponseEntity.status(HttpStatus.OK).body(service.getMonopatinesConKmsMayorAyMenorA(minKms, maxKms));
    }

    @GetMapping("/monopatines/reportesConTiempoPausa")
    public ResponseEntity<?> getReportesMonopatinesConTiempoPausa() throws Exception{
        return ResponseEntity.status(HttpStatus.OK).body(service.getReportesMonopatinesConTiempoPausa());
    }

    @GetMapping("/monopatines/reportesSinTiempoPausa")
    public ResponseEntity<?> getReportesMonopatinesSinTiempoPausa() throws Exception{
        return ResponseEntity.status(HttpStatus.OK).body(service.getReportesMonopatinesSinTiempoPausa());
    }

    //ABM paradas
    @PostMapping("/paradas")
    public ResponseEntity<?> saveParada(@RequestBody Parada p) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(service.saveParada(p));
    }

    @DeleteMapping("/paradas/{id}")
    public ResponseEntity<?> deleteParada(@PathVariable Long id) throws Exception {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(service.deleteParada(id));
    }

    //Anular cuenta momentaneamente
    @PutMapping("/cuentas/anular/{id}")
    public ResponseEntity<?> anularCuentaMomentaneamente(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.anularCuentaMomentaneamente(id));
    }

    //agregar una nueva tarifa
    @PostMapping("/tarifas")
    public ResponseEntity<?> agregarTarifa(@RequestBody Tarifa t) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(tarifa_service.save(t));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ERROR. Revise los campos y vuelva a intentar.");
        }
    }
    //Total facturado
    @GetMapping("/tarifas/totalFacturadoEnAnio/{anio}/entreMes/{mes1}/y/{mes2}")
    public ResponseEntity<?> getFacturacionTotalEnAnioEntreMeses(@PathVariable int anio, @PathVariable int mes1,
                                                                @PathVariable int mes2){
        return ResponseEntity.status(HttpStatus.OK).body(tarifa_service.getFacturacionTotalEnAnioEntreMeses(anio,mes1,mes2));
    }
}
