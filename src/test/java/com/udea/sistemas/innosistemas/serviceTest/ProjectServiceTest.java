package com.udea.sistemas.innosistemas.serviceTest; // ¡Mismo paquete!

// Imports de la lógica de Project
import com.udea.sistemas.innosistemas.models.entity.Project;
import com.udea.sistemas.innosistemas.service.ProjectService;

// Imports de TODOS los repositorios (el patrón que ya funciona)
import com.udea.sistemas.innosistemas.repository.*;
import com.udea.sistemas.innosistemas.authentication.repository.RefreshTokenRepository;

// Imports de Spring y Pruebas
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(properties = {
        // Le damos las variables JWT falsas que necesita para arrancar
        "JWT_SECRET=estoesunsecretodePruebaSuperLargo1234567890",
        "JWT_EXPIRATION=3600",
        "JWT_REFRESH_EXPIRATION=86400"
})
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class,
        JpaRepositoriesAutoConfiguration.class
})
class ProjectServiceTest {

    // --- Mocks para TODOS los repositorios (para que Spring no falle) ---
    @MockBean private CourseRepository courseRepository;
    @MockBean private UserRepository userRepository;
    @MockBean private ProjectRepository projectRepository; // Este es el que usaremos
    @MockBean private TeamRepository teamRepository;
    @MockBean private UsersTeamRepository usersTeamRepository;
    @MockBean private RefreshTokenRepository refreshTokenRepository;

    // --- Inyectamos el servicio que SÍ queremos probar ---
    @Autowired
    private ProjectService projectService;


    @Test
    void testGetProject() {
        // --- ARRANGE (Preparar) ---
        // 1. Creamos un proyecto falso
        Project proyectoFalso = new Project();
        proyectoFalso.setId(100);
        proyectoFalso.setNameProject("Proyecto de Prueba");
        proyectoFalso.setDescriptions("Desc...");
        proyectoFalso.setCourseId(1);
        proyectoFalso.setIs_deleted(false);

        // 2. Le decimos a Mockito: "Cuando llamen a projectRepository.findById(100),
        //    devuelve este proyecto falso."
        when(projectRepository.findById(100)).thenReturn(Optional.of(proyectoFalso));

        // --- ACT (Actuar) ---
        // 3. Llamamos al método real del servicio
        Project resultado = projectService.getProject(100);

        // --- ASSERT (Verificar) ---
        // 4. Verificamos que el resultado es el esperado
        assertNotNull(resultado);
        assertEquals(100, resultado.getId());
        assertEquals("Proyecto de Prueba", resultado.getNameProject());

        // 5. Verificamos que el mock fue llamado
        verify(projectRepository, times(2)).findById(100);
    }
}