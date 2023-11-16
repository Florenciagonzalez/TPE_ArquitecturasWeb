package com.tpe.monopatines.services;


import com.tpe.monopatines.modelos.dto.ViajeDTO;
import com.tpe.monopatines.modelos.Monopatin;
import com.tpe.monopatines.modelos.Viaje;
import com.tpe.monopatines.repository.MonopatinRepository;
import com.tpe.monopatines.repository.ViajeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class ViajeService {
    @Autowired
    private ViajeRepository repository;
    @Autowired
    private MonopatinRepository repo_mono;

    @Transactional
    public ViajeDTO save(ViajeDTO v) {
        try {
            Monopatin m = this.repo_mono.findById(v.getId_monopatin()).get();
            Viaje nuevo = new Viaje(m, v.getId_usuario(), v.getFecha_hora_inicio(), v.getFecha_hora_fin(),
                v.getKilometros(), v.getTiempo_pausa(),v.getInicio_pausa(), v.isEn_pausa());
            this.repository.save(nuevo);
            return new ViajeDTO(nuevo);
        }catch (Exception e) {
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public List<ViajeDTO> getTotalViajesEnAnioEntreMeses(int anio, int mes1, int mes2){
        try {
            return repository.getTotalViajesEnAnioEntreMeses(anio,mes1,mes2);
        }catch (Exception e) {
            throw e;
        }
    }

}
