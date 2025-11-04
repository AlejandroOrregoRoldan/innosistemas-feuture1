package com.udea.sistemas.innosistemas.serviceTest;

// Imports de la lógica de negocio
import com.udea.sistemas.innosistemas.models.entity.Course;
import com.udea.sistemas.innosistemas.service.CourseService;
import com.udea.sistemas.innosistemas.models.dto.CourseDto;

// Imports de TODOS los repositorios
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

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(properties = {
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
class CourseServiceTest {

    // --- INICIO DE LA CORRECCIÓN ---
    // Le decimos a Spring que cree Mocks para TODOS los repositorios
    // para que los otros servicios (como AuthService) no fallen.

    @MockBean
    private CourseRepository courseRepository; // El que usamos

    @MockBean
    private UserRepository userRepository; // El que causó el error

    @MockBean
    private ProjectRepository projectRepository; //

    @MockBean
    private TeamRepository teamRepository; //

    @MockBean
    private UsersTeamRepository usersTeamRepository; //

    @MockBean
    private RefreshTokenRepository refreshTokenRepository; //

    // --- FIN DE LA CORRECCIÓN ---


    // Inyectamos el servicio real que SÍ queremos probar
    @Autowired
    private CourseService courseService;


    @Test
    void testGetAllCourses() {
        // --- ARRANGE (Preparar) ---
        Course cursoFalso = new Course();
        cursoFalso.setId(1);
        cursoFalso.setNameCourse("Curso de Prueba");
        cursoFalso.setIsDeleted(false);

        when(courseRepository.findAll()).thenReturn(Collections.singletonList(cursoFalso));

        // --- ACT (Actuar) ---
        List<CourseDto> resultados = courseService.getAllCourses();

        // --- ASSERT (Verificar) ---
        assertNotNull(resultados);
        assertEquals(1, resultados.size());
        assertEquals("Curso de Prueba", resultados.get(0).nameCourse());
        verify(courseRepository, times(1)).findAll();
    }

    @Test
    void testCreateCourse() {
        // --- ARRANGE (Preparar) ---
        String nombreCurso = "Nuevo Curso";

        Course cursoGuardado = new Course();
        cursoGuardado.setId(1);
        cursoGuardado.setNameCourse(nombreCurso);
        cursoGuardado.setIsDeleted(false);

        when(courseRepository.save(any(Course.class))).thenReturn(cursoGuardado);
        when(courseRepository.existsByNameCourseIgnoreCase(nombreCurso)).thenReturn(false);

        // --- ACT (Actuar) ---
        Course resultado = courseService.createCourse(nombreCurso);

        // --- ASSERT (Verificar) ---
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals(nombreCurso, resultado.getNameCourse());
    }
}