package com.tpe.mantenimiento.service;

import com.tpe.mantenimiento.modelos.Monopatin;
import com.tpe.mantenimiento.modelos.dto.ReportePorTiempo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import com.tpe.mantenimiento.modelos.Mantenimiento;
import com.tpe.mantenimiento.modelos.dto.MantenimientoDTO;
import com.tpe.mantenimiento.repository.mongo.MantenimientoRepository;

import jakarta.transaction.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MantenimientoService {
	
	@Autowired
	private MantenimientoRepository mantenimientoRepository;
    @Autowired
    private RestTemplate restTemplate;
	
	@Transactional
    public Mantenimiento save(Mantenimiento m) throws Exception{
        try{
            return this.mantenimientoRepository.save(m);
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public MantenimientoDTO update(String id, MantenimientoDTO m) throws Exception{
        try {
            if (this.mantenimientoRepository.existsById(id)) {
                Mantenimiento antiguo = this.mantenimientoRepository.findById(id).get();
                antiguo.setId_monopatin(m.getId_monopatin());
                antiguo.setFecha_inicio(m.getFecha_inicio());
                antiguo.setFecha_fin(m.getFecha_fin());
                antiguo.setEsta_reparado(m.isEsta_reparado());

                this.mantenimientoRepository.save(antiguo);
                return new MantenimientoDTO(antiguo);
            }else{
                throw new Exception();
            }
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public boolean delete(String id) throws Exception {
        try {
            if(this.mantenimientoRepository.existsById(id)) {
                this.mantenimientoRepository.deleteById(id);
                return true;
            }
            else{
                throw new Exception();
            }
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public MantenimientoDTO getById(String id) throws Exception{
        try{
            return new MantenimientoDTO(this.mantenimientoRepository.findById(id).orElseThrow());
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public ResponseEntity enviarMonopatinMantenimiento(String token, Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<Monopatin> response = restTemplate.exchange(
                "http://localhost:8004/monopatines/" + id,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<Monopatin>() {});

        if(response.getStatusCode().is2xxSuccessful()){
            Monopatin m = response.getBody();
            if(m.getEstado().equals("disponible")){
                Mantenimiento mant = new Mantenimiento(id, LocalDateTime.now(), null, false);
                this.mantenimientoRepository.save(mant);
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

    public ResponseEntity sacarMonopatinDeMantenimiento(String token, Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<Monopatin> response = restTemplate.exchange(
                "http://localhost:8004/monopatines/" + id,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<Monopatin>() {});

        if (response.getStatusCode().is2xxSuccessful()){
            Monopatin monopatin = response.getBody();
            if(monopatin.getEstado().equals("en mantenimiento")) {
                Mantenimiento mant = this.mantenimientoRepository.findByIdMonopatinAndEstaReparado(id, false);
                if (mant != null) {
                    mant.setEsta_reparado(true);
                    mant.setFecha_fin(LocalDateTime.now());
                    this.mantenimientoRepository.save(mant);
                    monopatin.setEstado("disponible");
                    HttpEntity<Monopatin> requestEntity2 = new HttpEntity<>(monopatin, headers);
                    ResponseEntity<Monopatin> response2 = restTemplate.exchange(
                            "http://localhost:8004/monopatines/" + id,
                            HttpMethod.PUT,
                            requestEntity2,
                            new ParameterizedTypeReference<Monopatin>() {
                            });

                    return response2;
                }
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El monopatin que intenta sacar de mantenimiento no fue encontrado");
    }

    public ResponseEntity getReportesMonopatinesConTiempoPausa(String token){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<List<ReportePorTiempo>> response = restTemplate.exchange(
                "http://localhost:8004/monopatines/reportesConTiempoPausa",
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<List<ReportePorTiempo>>() {});

        if(response != null) {
            return response;
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR. No se encontraron monopatines con tiempo de pausa");
    }

    public ResponseEntity getReportesMonopatinesSinTiempoPausa(String token){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<List<ReportePorTiempo>> response = restTemplate.exchange(
                "http://localhost:8004/monopatines/reportesSinTiempoPausa",
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<List<ReportePorTiempo>>() {});

        if(response != null) {
            return response;
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR. No se encontraron monopatines sin tiempo de pausa");
    }


}
