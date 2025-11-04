package com.udea.sistemas.innosistemas;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

// ¡YA NO EXCLUIMOS NADA! Cargamos el contexto completo.
@SpringBootTest(properties = {
		// Le decimos a Spring que use H2 para esta prueba
		"spring.datasource.url=jdbc:h2:mem:testdb",
		"spring.datasource.driverClassName=org.h2.Driver",
		"spring.datasource.username=sa",
		"spring.datasource.password=password",
		"spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
		"spring.jpa.hibernate.ddl-auto=update", // Que cree las tablas

		// Le damos las variables JWT falsas que necesita
		"JWT_SECRET=estoesunsecretodePruebaSuperLargo1234567890",
		"JWT_EXPIRATION=3600",
		"JWT_REFRESH_EXPIRATION=86400"
})
class InnosistemasApplicationTests {

	@Test
	void contextLoads() {
		// Esta prueba ahora SÍ cargará el contexto completo.
		// JaCoCo se "enganchará" aquí.
	}
}