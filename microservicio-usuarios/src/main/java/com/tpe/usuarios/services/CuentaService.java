package com.tpe.usuarios.services;

import com.tpe.usuarios.models.dto.CuentaDTO;
import com.tpe.usuarios.models.Cuenta;
import com.tpe.usuarios.repository.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CuentaService {
    @Autowired
    private CuentaRepository repository;

    @Transactional
    public Cuenta save(Cuenta c) throws Exception {
        try {
            return this.repository.save(c);
        }catch (Exception e) {
            throw e;
        }
    }

    @Transactional
    public CuentaDTO update(Long id, CuentaDTO c) throws Exception {
        try {
            if(this.repository.existsById(id)) {
                Cuenta antigua = this.repository.findById(id).get();
                antigua.setSaldo(c.getSaldo());
                antigua.setEstado(c.getEstado());

                this.repository.save(antigua);
                return new CuentaDTO(antigua);
            }else{
                throw new Exception();
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional
    public boolean anularCuenta(Long id) throws Exception{
        try{
            if(this.repository.existsById(id)){
                Cuenta anulada = this.repository.findById(id).get();
                anulada.setEstado("inhabilitada");
                this.repository.save(anulada);

                return true;
            }else {
                throw new Exception();
            }
        }catch (Exception e){
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public CuentaDTO getById(Long id) throws Exception {
        try {
            return new CuentaDTO(this.repository.findById(id).get());
        }catch (Exception e) {
            throw e;
        }
    }



}
