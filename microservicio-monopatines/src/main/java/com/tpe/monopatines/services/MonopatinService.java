package com.tpe.monopatines.services;

import com.tpe.monopatines.modelos.dto.MonopatinCantViajesDTO;
import com.tpe.monopatines.modelos.dto.MonopatinDTO;
import com.tpe.monopatines.modelos.dto.ReportePorTiempo;
import com.tpe.monopatines.modelos.Monopatin;
import com.tpe.monopatines.repository.MonopatinRepository;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service

public class MonopatinService {
    @Autowired
    private MonopatinRepository repositorio;

    @Transactional
    public Monopatin save(Monopatin m) throws Exception {
        try{
            return this.repositorio.save(m);
        }catch (Exception e) {
            throw e;
        }
    }

    @Transactional
    public MonopatinDTO update(Long id, Monopatin m) throws Exception {
        try {
            if(this.repositorio.existsById(id)){
                Monopatin antiguo = this.repositorio.findById(id).get();
                antiguo.setEstado(m.getEstado());
                antiguo.setLatitud(m.getLatitud());
                antiguo.setLongitud(m.getLongitud());
                antiguo.setKilometros_recorridos(m.getKilometros_recorridos());

                this.repositorio.save(antiguo);
                return new MonopatinDTO(antiguo);
            }else{
                throw new Exception();
            }
        }catch (Exception e) {
            throw e;
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
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public MonopatinDTO getById(Long id) throws Exception{
        try{
            return new MonopatinDTO(this.repositorio.findById(id).get());
        }catch (Exception e) {
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public List<MonopatinDTO> getMonopatinesDisponiblesEnZona(double latitud, double longitud) throws Exception {
        try {
            //establezco un rango
            double rangoBusqueda = 30;
            double latitudMin = latitud - rangoBusqueda;
            double latitudMax = latitud + rangoBusqueda;
            double longitudMin =  longitud - rangoBusqueda;
            double longitudMax =  longitud + rangoBusqueda;

            return this.repositorio.getMonopatinesDisponiblesEnZona(latitudMin, latitudMax, longitudMin, longitudMax);
        }catch (Exception e) {
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public String getReporteOpVsMant() throws Exception {
        try {
            int cant_Mant = repositorio.getCantEnEstado("mantenimiento");
            int cant_enOp = repositorio.getCantEnEstado("en uso");
            cant_enOp += repositorio.getCantEnEstado("disponible");

            return "Monopatines en operacion: " + cant_enOp + ", Monopatines en mantenimiento: " + cant_Mant;
        }catch (Exception e) {
            throw  e;
        }
    }

    @Transactional(readOnly = true)
    public List<MonopatinCantViajesDTO> getConMasCantViajeEnAnio(int anio, Long cant_viajes) {
        try {
            return  this.repositorio.getConMasCantViajeEnAnio(anio,cant_viajes);
        }catch (Exception e) {
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public List<ReportePorTiempo> getReportesConTiempoPausa(){
        try{
            return this.repositorio.getReportesConTiempoPausa();
        }catch (Exception e){
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public List<ReportePorTiempo> getReportesSinTiempoPausa(){
        try{
            return this.repositorio.getReportesSinTiempoPausa();
        }catch (Exception e){
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public List<MonopatinDTO> getConKmsEntre(double minKms, double maxKms){
        try{
            return this.repositorio.getConKmsEntre(minKms, maxKms);
        }catch (Exception e){
            throw e;
        }
    }

}
