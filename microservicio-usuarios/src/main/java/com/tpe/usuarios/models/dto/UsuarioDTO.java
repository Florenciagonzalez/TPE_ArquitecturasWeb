package com.tpe.usuarios.models.dto;

import com.tpe.usuarios.models.Usuario;
import lombok.Data;

import java.io.Serializable;

@Data
public class UsuarioDTO implements Serializable {
    private Long id;
    private String email;
    private Long nro_celular;
    private String nombre;
    private String apellido;
    private Long id_cuenta;
    private String rol;
    private String password;

    public UsuarioDTO() {
    }

    public UsuarioDTO(Usuario u) {
        this.id = u.getId();
        this.email = u.getEmail();
        this.nro_celular = u.getNro_celular();
        this.nombre = u.getNombre();
        this.apellido = u.getApellido();
        this.id_cuenta = u.getId_cuenta().getId();
        this.rol = u.getRol().getNombre();
        this.password = u.getPassword();
    }

}
