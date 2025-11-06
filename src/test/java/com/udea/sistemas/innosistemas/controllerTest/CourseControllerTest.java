package com.udea.sistemas.innosistemas.controllerTest; // <-- Paquete correcto

import com.udea.sistemas.innosistemas.controllers.CourseController;
import com.udea.sistemas.innosistemas.service.CourseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CourseControllerTest { // <-- Nombre de clase correcto

    @Mock
    private CourseService courseService;

    @InjectMocks
    private CourseController courseController;

    // Aquí puedes empezar a agregar tus pruebas para el CourseController
    @Test
    void testGetAllCourses() {
        // Ejemplo:
        // when(courseService.getAllCourses()).thenReturn(List.of());
        // ...
        assertTrue(true); // Placeholder
    }
}