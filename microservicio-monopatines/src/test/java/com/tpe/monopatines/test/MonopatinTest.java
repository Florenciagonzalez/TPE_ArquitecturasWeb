package com.tpe.monopatines.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.tpe.monopatines.modelos.Monopatin;
import com.tpe.monopatines.modelos.dto.MonopatinDTO;
import com.tpe.monopatines.repository.MonopatinRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class MonopatinTest {
	static Monopatin m;
	
	@Autowired
	private MonopatinRepository monopatinRepository;
	
	@BeforeAll
	public static void cargarMonopatin() {
		m = new Monopatin("disponible", 3, 200L, 234, 245);
	}
	
	@Test
	public void testObtenerMonopatinesConKmsEntre() {
		Monopatin guardado = monopatinRepository.save(m);	
		assertNotNull(guardado);
	}
	
	@TestFactory
	Stream<DynamicTest> testDinamicoMonopatines() {
		monopatinRepository.save(m);	
		List<MonopatinDTO> dto = monopatinRepository.getConKmsEntre(2, 6);
		return dto.stream().map(dom -> DynamicTest.dynamicTest("Test monopatin", ()->{
			Assertions.assertEquals(dom.getId(), m.getId());
		}));
	}

}
