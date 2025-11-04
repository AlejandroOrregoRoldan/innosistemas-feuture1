package com.udea.sistemas.innosistemas.serviceTest;

import com.udea.sistemas.innosistemas.models.entity.Course;
import com.udea.sistemas.innosistemas.repository.CourseRepository;
import com.udea.sistemas.innosistemas.service.CourseService;
import com.udea.sistemas.innosistemas.models.dto.CourseDto;

// Volvemos a usar Mockito puro (como tu amigo)
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // ¡Sin @SpringBootTest!
class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseService courseService;

    @Test
    void testGetAllCourses() {
        // (El código de la prueba no cambia)
        Course cursoFalso = new Course();
        cursoFalso.setId(1);
        cursoFalso.setNameCourse("Curso de Prueba");
        cursoFalso.setIsDeleted(false);
        when(courseRepository.findAll()).thenReturn(Collections.singletonList(cursoFalso));
        List<CourseDto> resultados = courseService.getAllCourses();
        assertNotNull(resultados);
        assertEquals(1, resultados.size());
        verify(courseRepository, times(1)).findAll();
    }

    @Test
    void testCreateCourse() {
        // (El código de la prueba no cambia)
        String nombreCurso = "Nuevo Curso";
        Course cursoGuardado = new Course();
        cursoGuardado.setId(1);
        cursoGuardado.setNameCourse(nombreCurso);
        cursoGuardado.setIsDeleted(false);
        when(courseRepository.save(any(Course.class))).thenReturn(cursoGuardado);
        when(courseRepository.existsByNameCourseIgnoreCase(nombreCurso)).thenReturn(false);
        Course resultado = courseService.createCourse(nombreCurso);
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
    }
}