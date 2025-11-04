package com.udea.sistemas.innosistemas.serviceTest;

import com.udea.sistemas.innosistemas.models.entity.Project;
import com.udea.sistemas.innosistemas.service.ProjectService;
import com.udea.sistemas.innosistemas.repository.ProjectRepository;
import com.udea.sistemas.innosistemas.repository.UserRepository;

// Volvemos a usar Mockito puro
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // ¡Sin @SpringBootTest!
class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ProjectService projectService;

    @Test
    void testGetProject() {
        // (El código de la prueba no cambia)
        Project proyectoFalso = new Project();
        proyectoFalso.setId(100);
        proyectoFalso.setNameProject("Proyecto de Prueba");
        when(projectRepository.findById(100)).thenReturn(Optional.of(proyectoFalso));
        Project resultado = projectService.getProject(100);
        assertNotNull(resultado);
        assertEquals(100, resultado.getId());
        verify(projectRepository, times(2)).findById(100); // Verificamos las 2 llamadas
    }
}