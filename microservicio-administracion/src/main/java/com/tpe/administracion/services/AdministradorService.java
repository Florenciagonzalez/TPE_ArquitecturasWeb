package com.tpe.administracion.services;

import com.tpe.administracion.models.dto.MonopatinCantViajesDTO;
import com.tpe.administracion.models.clases.Mantenimiento;
import com.tpe.administracion.models.clases.Monopatin;
import com.tpe.administracion.models.clases.Parada;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdministradorService {
    @Autowired
    private RestTemplate restTemplate;

    
    //ABM monopatines
    public ResponseEntity saveMonopatin(Monopatin m) {
        HttpHeaders headers = new HttpHeaders();

        Monopatin nuevo = new Monopatin(m);
        HttpEntity<Monopatin> requestEntity = new HttpEntity<>(nuevo, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:8004/monopatines",
                HttpMethod.POST,
                requestEntity,
                String.class);

        return response;
    }
    public ResponseEntity deleteMonopatin(Long id)  {
        HttpHeaders headers = new HttpHeaders();

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:8004/monopatines/" + id,
                HttpMethod.DELETE,
                requestEntity,
                String.class);

        return response;
    }

    public ResponseEntity enviarMonopatinMantenimiento(Long id) {
        HttpHeaders headers = new HttpHeaders();

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<Monopatin> response = restTemplate.exchange(
                "http://localhost:8004/monopatines/" + id,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<Monopatin>() {});

        if(response.getStatusCode().is2xxSuccessful()){
            Monopatin m = response.getBody();
            if(m.getEstado().equals("disponible")){
               this.nuevoMantenimiento(id);
                m.setEstado("en mantenimiento");
                HttpEntity<Monopatin> requestEntity2 = new HttpEntity<>(m, headers);
                ResponseEntity<Monopatin> response2 = restTemplate.exchange(
                        "http://localhost:8004/monopatines/" + id,
                            HttpMethod.PUT,
                            requestEntity2,
                            new ParameterizedTypeReference<Monopatin>() {});

                return response2;
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El monopatin que intenta añadir a mantenimiento no fue encontrado");
    }

    private ResponseEntity nuevoMantenimiento(Long id) {
        HttpHeaders headers = new HttpHeaders();

        Mantenimiento nuevo = new Mantenimiento();
        nuevo.setId_monopatin(id);
        nuevo.setFecha_inicio(LocalDateTime.now());
        nuevo.setFecha_fin(null);
        nuevo.setEsta_reparado(false);
        HttpEntity<Mantenimiento> requestEntity = new HttpEntity<>(nuevo, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:8003/mantenimiento",
                HttpMethod.POST,
                requestEntity,
                String.class);

        return response;
    }

    public ResponseEntity sacarMonopatinDeMantenimiento(Long id) {
        HttpHeaders headers = new HttpHeaders();

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<Monopatin> response = restTemplate.exchange(
                "http://localhost:8004/monopatines/" + id,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<Monopatin>() {});

        if (response.getStatusCode().is2xxSuccessful()){
            Monopatin monopatin = response.getBody();
            if(monopatin.getEstado().equals("en mantenimiento")) {
                this.terminarMantenimiento(id);
                monopatin.setEstado("disponible");
                HttpEntity<Monopatin> requestEntity2 = new HttpEntity<>(monopatin, headers);
                ResponseEntity<Monopatin> response2 = restTemplate.exchange(
                        "http://localhost:8004/monopatines/" + id,
                        HttpMethod.PUT,
                        requestEntity2,
                        new ParameterizedTypeReference<Monopatin>() {});

                return response2;
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El monopatin que intenta sacar de mantenimiento no fue encontrado");
    }

    private ResponseEntity terminarMantenimiento(Long id) {
        HttpHeaders headers = new HttpHeaders();

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<Mantenimiento> response = restTemplate.exchange(
                "http://localhost:8003/mantenimiento/activoPorIdMonopatin/" + id,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<Mantenimiento>() {});

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() !=null){
            Mantenimiento actual = response.getBody();
            actual.setEsta_reparado(true);
            actual.setFecha_fin(LocalDateTime.now());

            HttpEntity<Mantenimiento> requestEntity2 = new HttpEntity<>(actual,headers);
            ResponseEntity<Mantenimiento> response2 = restTemplate.exchange(
                    "http://localhost:8003/mantenimiento/" + actual.getId(),
                    HttpMethod.PUT,
                    requestEntity2,
                    new ParameterizedTypeReference<Mantenimiento>() {});

            return response2;
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El mantenimiento que desea terminar no fue encontrado");
        }
    }

    //Reportes monopatines

    public ResponseEntity getReporteMonopatinesOpVsMant(){
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:8004/monopatines/cantEnMantVsOp",
                    HttpMethod.GET,
                    requestEntity,
                    String.class);

        if(response != null) {
            return response;
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR. No se encontraron monopatines con las caracteristicas ingresadas,");
    }

    public ResponseEntity getReportesMonopatinesConTiempoPausa(){
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:8004/monopatines/reportesConTiempoPausa",
                HttpMethod.GET,
                requestEntity,
                String.class);

        if(response != null) {
            return response;
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR. No se encontraron monopatines con tiempo de pausa");
    }

    public ResponseEntity getReportesMonopatinesSinTiempoPausa(){
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:8004/monopatines/reportesSinTiempoPausa",
                HttpMethod.GET,
                requestEntity,
                String.class);

        if(response != null) {
            return response;
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR. No se encontraron monopatines sin tiempo de pausa");
    }

    public ResponseEntity getMonopatinesConMasCantViajeEnAnio(int anio, Long cant_viajes){
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> requesEntity = new HttpEntity<>(headers);
        ResponseEntity<List<MonopatinCantViajesDTO>> response = restTemplate.exchange(
                "http://localhost:8004/monopatines/anio/" + anio + "/cantViajesMayorA/" + cant_viajes,
                HttpMethod.GET,
                requesEntity,
                new ParameterizedTypeReference<List<MonopatinCantViajesDTO>>() {});

        if(response != null) {
            return response;
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR. No se encontraron monopatines con las caracteristicas ingresadas,");
    }

    public ResponseEntity getMonopatinesConKmsMayorAyMenorA(double minKms, double maxKms){
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> requesEntity = new HttpEntity<>(headers);
        ResponseEntity<List<Monopatin>> response = restTemplate.exchange(
                "http://localhost:8004/monopatines/conKmsEntre/min/" + minKms + "/max/"+ maxKms,
                HttpMethod.GET,
                requesEntity,
                new ParameterizedTypeReference<List<Monopatin>>() {});

        if(response != null) {
            return response;
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR. No se encontraron monopatines con las caracteristicas ingresadas,");

    }


    public ResponseEntity deleteMantenimiento(Long id) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:8003/mantenimiento/" + id,
                HttpMethod.DELETE,
                requestEntity,
                String.class);

        return response;
    }


    //ABM paradas
    public ResponseEntity saveParada(Parada p) {
        HttpHeaders headers = new HttpHeaders();

        Parada nueva = new Parada(p);
        HttpEntity<Parada> requestEntity = new HttpEntity<>(nueva, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                        "http://localhost:8005/paradas",
                            HttpMethod.POST,
                            requestEntity,
                            String.class);

        return response;
    }

    public ResponseEntity deleteParada(Long id) {
        HttpHeaders headers = new HttpHeaders();

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                        "http://localhost:8005/paradas/" + id,
                            HttpMethod.DELETE,
                            requestEntity,
                            String.class);

        return response;
    }

    //Anular cuenta momementaneamente

    public ResponseEntity anularCuentaMomentaneamente(Long id) {
        HttpHeaders headers = new HttpHeaders();

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:8001/cuentas/anular/" + id,
                    HttpMethod.PUT,
                    requestEntity,
                    String.class);

        if(response != null){
            return ResponseEntity.status(HttpStatus.OK).body("La cuenta fue anulada");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La cuenta que desea anular no fue encontrada");
    }
}




