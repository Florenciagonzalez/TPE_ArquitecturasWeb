package com.tpe.administracion.services;

import com.tpe.administracion.models.dto.TarifaDTO;
import com.tpe.administracion.models.dto.Viaje;
import com.tpe.administracion.models.entidades.Tarifa;
import com.tpe.administracion.repository.TarifaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TarifaService {
    @Autowired
    private TarifaRepository repositorio;
    @Autowired
    private RestTemplate restTemplate;

    @Transactional
    public Tarifa save(Tarifa t) throws Exception {
        try{
            return this.repositorio.save(t);
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public Tarifa definirTarifaExtra(Long id, Double tarifa_extra)  {
        Tarifa actualizada = this.repositorio.findById(id).orElseThrow();
        actualizada.setValor_pausaExtendida(tarifa_extra);
        return actualizada;

    }

    @Transactional(readOnly = true)
    public TarifaDTO getById(Long id) {
        Tarifa t = this.repositorio.findById(id).orElseThrow();
        return new TarifaDTO(t);
    }

    //Facturacion del año, entre meses
    public ResponseEntity getFacturacionTotalEnAnioEntreMeses(int anio, int mes1, int mes2) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<List<Viaje>> response = restTemplate.exchange(
                "http://localhost:8004/viajes/totalViajesEn/anio/" +anio+ "/entreMes/" +
                        mes1+ "/y/" + mes2,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<List<Viaje>>(){});

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null){
            double total = this.getFacturacion(response.getBody(), anio);

            return ResponseEntity.ok("El total facturado entre los meses " + mes1 + " y " + mes2 + " es: $" + total);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron viajes en esas fechas");
    }

    private double getFacturacion(List<Viaje> viajes, int anio){
        double total = 0.0;
        int mins_max_pausa = 15;
        for(Viaje v : viajes){
            Tarifa t = this.repositorio.getTarifaEnFecha(this.getFechaALocalDate(v.getFecha_hora_inicio()));
            Long duracion_viaje = this.getTiempoViajeEnMins(v.getFecha_hora_inicio(), v.getFecha_hora_fin());
            if(v.getTiempo_pausa() > mins_max_pausa){
                Long duracion_inicio_pausa = this.getTiempoViajeEnMins(v.getFecha_hora_inicio(), v.getInicio_pausa());
                total += (duracion_inicio_pausa + mins_max_pausa) * t.getValor();
                total += (duracion_viaje - (duracion_inicio_pausa + mins_max_pausa)) * t.getValor_pausaExtendida();
            }else{
                total += duracion_viaje * t.getValor();
            }
        }
        return total;
    }

    private LocalDate getFechaALocalDate(LocalDateTime fecha){
        return fecha.toLocalDate();
    }

    private Long getTiempoViajeEnMins(LocalDateTime inicio, LocalDateTime fin){
        return Duration.between(inicio,fin).toMinutes();
    }


}
