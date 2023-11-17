package com.tpe.mantenimiento.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tpe.mantenimiento.modelos.Mantenimiento;
import com.tpe.mantenimiento.modelos.dto.MantenimientoDTO;
import com.tpe.mantenimiento.repository.mongo.MantenimientoRepository;

import jakarta.transaction.Transactional;

@Service
public class MantenimientoService {
	
	@Autowired
	private MantenimientoRepository mantenimientoRepository;
	
	@Transactional
    public Mantenimiento save(Mantenimiento m) throws Exception{
        try{
            return this.mantenimientoRepository.save(m);
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public MantenimientoDTO update(Long id, MantenimientoDTO m) throws Exception{
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
    public boolean delete(Long id) throws Exception {
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
    public MantenimientoDTO getById(Long id) throws Exception{
        try{
            return new MantenimientoDTO(this.mantenimientoRepository.findById(id).orElseThrow());
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

   @Transactional
    public MantenimientoDTO getMantActivoByMonopatinId(Long id_monopatin) throws Exception{
        try{
            Mantenimiento m = this.mantenimientoRepository.findByIdMonopatinAndEstaReparado(id_monopatin, false);
            return new MantenimientoDTO(m);
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
