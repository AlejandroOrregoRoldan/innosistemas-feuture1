package com.udea.sistemas.innosistemas.serviceTest;

import com.udea.sistemas.innosistemas.models.entity.Course;
import com.udea.sistemas.innosistemas.repository.CourseRepository;
import com.udea.sistemas.innosistemas.service.CourseService;
import com.udea.sistemas.innosistemas.models.dto.CourseDto;

// --- INICIO DE CAMBIOS ---
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
// --- FIN DE CAMBIOS ---

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// --- INICIO DE CAMBIOS ---
// 1. Ya NO usamos @SpringBootTest ni @EnableAutoConfiguration
// 2. Usamos la extensión de Mockito para JUnit 5
@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    // 3. Usamos @Mock (de Mockito) en lugar de @MockBean (de Spring)
    @Mock
    private CourseRepository courseRepository;

    // 4. Usamos @InjectMocks (de Mockito) en lugar de @Autowired (de Spring)
    //    Esto crea una instancia de CourseService e le inyecta el mock de arriba.
    @InjectMocks
    private CourseService courseService;

    // 5. El resto de las pruebas son idénticas
    // --- FIN DE CAMBIOS ---

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