package com.tpe.monopatines.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.tpe.monopatines.repository.MonopatinRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class MonopatinTest {
	
	@Autowired
	private MonopatinRepository monopatinRepository;
	
	@Test
	public void testObtenerMonopatinesConKmsEntre() {
		
	}

}
