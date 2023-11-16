package com.tpe.mantenimiento.service;

import com.tpe.mantenimiento.modelos.dto.MantenimientoDTO;
import com.tpe.mantenimiento.modelos.Mantenimiento;
import com.tpe.mantenimiento.repository.MantenimientoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MantenimientoService {
    @Autowired
    private MantenimientoRepository repositorio;

    @Transactional
    public Mantenimiento save(Mantenimiento m) throws Exception{
        try{
            return this.repositorio.save(m);
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public MantenimientoDTO update(Long id, MantenimientoDTO m) throws Exception{
        try {
            if (this.repositorio.existsById(id)) {
                Mantenimiento antiguo = this.repositorio.findById(id).get();
                antiguo.setId_monopatin(m.getId_monopatin());
                antiguo.setFecha_inicio(m.getFecha_inicio());
                antiguo.setFecha_fin(m.getFecha_fin());
                antiguo.setEsta_reparado(m.isEsta_reparado());

                this.repositorio.save(antiguo);
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
            if(this.repositorio.existsById(id)) {
                this.repositorio.deleteById(id);
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
            return new MantenimientoDTO(this.repositorio.findById(id).orElseThrow());
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

   @Transactional
    public MantenimientoDTO getMantActivoByMonopatinId(Long id_monopatin) throws Exception{
        try{
            Mantenimiento m = this.repositorio.getMantActivoByMonopatinId(id_monopatin);
            return new MantenimientoDTO(m);
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }


}
