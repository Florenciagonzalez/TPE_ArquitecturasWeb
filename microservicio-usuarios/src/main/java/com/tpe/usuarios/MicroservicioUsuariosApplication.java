package com.tpe.usuarios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class MicroservicioUsuariosApplication {
	public static void main(String[] args) {
		SpringApplication.run(MicroservicioUsuariosApplication.class, args);
	}

}
