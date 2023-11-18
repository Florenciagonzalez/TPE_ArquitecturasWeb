package com.tpe.usuarios.services;

import com.tpe.usuarios.models.Cuenta;
import com.tpe.usuarios.models.Monopatin;
import com.tpe.usuarios.models.Rol;
import com.tpe.usuarios.models.dto.UsuarioDTO;
import com.tpe.usuarios.models.Usuario;
import com.tpe.usuarios.repository.CuentaRepository;
import com.tpe.usuarios.repository.RolRepository;
import com.tpe.usuarios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository repository;
    @Autowired
    private CuentaRepository repo_cuenta;
    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public UsuarioDTO save(UsuarioDTO u) throws Exception{
        try{
            if(this.repo_cuenta.existsById(u.getId_cuenta()) && this.rolRepository.existsById(u.getRol())){
                Cuenta c = this.repo_cuenta.findById(u.getId_cuenta()).get();
                Rol rol = this.rolRepository.findById(u.getRol()).get();
                String encryptedPass = passwordEncoder.encode(u.getPassword());
                Usuario nuevo = new Usuario(u.getEmail(), u.getNro_celular(), u.getNombre(), u.getApellido(), c, rol, encryptedPass);
                this.repository.save(nuevo);
                return new UsuarioDTO(nuevo);
            }else {
                throw new Exception();
            }
        }catch (Exception e) {
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public UsuarioDTO getById(Long id) throws Exception {
        try{
            return new UsuarioDTO(this.repository.findById(id).orElseThrow());
        }catch (Exception e){
            throw e;
        }
    }

    public ResponseEntity getMonopatinesDisponiblesEnZona(double latitud, double longitud) {
        HttpHeaders headers = new HttpHeaders();

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<List<Monopatin>> response = restTemplate.exchange(
                "http://localhost:8004/monopatines/usuario/disponiblesEnZona/latitud/" + latitud +
                        "/longitud/" + longitud,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<List<Monopatin>>() {});

        if (response.getStatusCode().is2xxSuccessful()){
            return response;
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron monopatines disponibles en su zona");

        }
    }
}
