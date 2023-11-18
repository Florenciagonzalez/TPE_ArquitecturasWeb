package com.tpe.usuarios.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.tpe.usuarios.models.Cuenta;
import com.tpe.usuarios.models.Monopatin;
import com.tpe.usuarios.models.Rol;
import com.tpe.usuarios.models.Usuario;
import com.tpe.usuarios.repository.UsuarioRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UsuarioTest {
	
	final static long ID = 10;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Test 
	public void testGuardarUsuario() {
		Rol rol = new Rol();
		rol.setNombre("USER");
		
		Cuenta cuenta = new Cuenta(LocalDate.now(), (float) 3440, "disponible");	
		Usuario usuario = new Usuario("prueba@gmail.com", 276391L, "test", "unitario", cuenta, rol, "pass");	
		Usuario usuarioGuardado = usuarioRepository.save(usuario);
		
		assertNotNull(usuarioGuardado);
        assertEquals("USER", usuarioGuardado.getRol().getNombre());
        assertTrue(usuarioGuardado.getRol().equals(rol));
	}
	
	@Test
	public void testBuscarUsuarioPorEmail() {
		String nombre = "user@gmail.com";
		Usuario usuario = usuarioRepository.findByEmail(nombre).get();
		assertThat(usuario.getEmail()).isEqualTo(nombre);
	}
	
	@Test
	public void testModificacionDeEmailUsuario() {
		String email = "nuevoEmail@gmail.com";	
		Usuario usuario = usuarioRepository.findById(ID).get();
		usuario.setEmail(email);
		assertEquals(usuario.getEmail(), email);
	}
	
}
