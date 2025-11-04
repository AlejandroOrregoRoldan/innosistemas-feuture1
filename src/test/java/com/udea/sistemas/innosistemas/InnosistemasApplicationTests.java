package com.udea.sistemas.innosistemas;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

// *** IMPORTS NECESARIOS (INCLUYENDO EL NUEVO) ***
import org.springframework.boot.autoconfigure.EnableAutoConfiguration; // <-- ESTE ES EL IMPORT CLAVE
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;

// Tus repositorios
import com.udea.sistemas.innosistemas.repository.CourseRepository;
import com.udea.sistemas.innosistemas.repository.ProjectRepository;
import com.udea.sistemas.innosistemas.repository.TeamRepository;
import com.udea.sistemas.innosistemas.repository.UserRepository;
import com.udea.sistemas.innosistemas.repository.UsersTeamRepository;
import com.udea.sistemas.innosistemas.authentication.repository.RefreshTokenRepository;

// *** ASÍ QUEDAN LAS ANOTACIONES ***

// 1. @SpringBootTest SÓLO tiene las propiedades JWT
@SpringBootTest(
		properties = {
				"JWT_SECRET=estoesunsecretodePruebaSuperLargo1234567890",
				"JWT_EXPIRATION=3600",
				"JWT_REFRESH_EXPIRATION=86400"
		}
)
// 2. AÑADIMOS la anotación para excluir la DB
@EnableAutoConfiguration(exclude = {
		DataSourceAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class,
		HibernateJpaAutoConfiguration.class,
		JpaRepositoriesAutoConfiguration.class
})
class InnosistemasApplicationTests {

	// 3. Mantenemos los mocks
	@MockBean private CourseRepository courseRepository;
	@MockBean private ProjectRepository projectRepository;
	@MockBean private TeamRepository teamRepository;
	@MockBean private UserRepository userRepository;
	@MockBean private UsersTeamRepository usersTeamRepository;
	@MockBean private RefreshTokenRepository refreshTokenRepository;

	@Test
	void contextLoads() {
		// Ahora sí, la prueba solo carga la seguridad (con JWT falsos)
		// e ignora completamente la base de datos.
	}
}