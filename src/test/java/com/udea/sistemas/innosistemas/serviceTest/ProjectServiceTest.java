package com.udea.sistemas.innosistemas.serviceTest;

import com.udea.sistemas.innosistemas.models.entity.Project;
import com.udea.sistemas.innosistemas.service.ProjectService;
import com.udea.sistemas.innosistemas.repository.ProjectRepository; // Importamos el repo
import com.udea.sistemas.innosistemas.repository.UserRepository; // Importamos el repo

// --- INICIO DE CAMBIOS ---
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
// --- FIN DE CAMBIOS ---

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// --- INICIO DE CAMBIOS ---
// 1. Usamos la extensión de Mockito
@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    // 2. Usamos @Mock para los repositorios
    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private UserRepository userRepository; // El servicio también usa este

    // 3. Usamos @InjectMocks para el servicio
    @InjectMocks
    private ProjectService projectService;

    // --- FIN DE CAMBIOS ---

    @Test
    void testGetProject() {
        // --- ARRANGE (Preparar) ---
        Project proyectoFalso = new Project();
        proyectoFalso.setId(100);
        proyectoFalso.setNameProject("Proyecto de Prueba");

        // El método getProject llama a findById 2 veces (una en existProject, otra en getProject)
        when(projectRepository.findById(100)).thenReturn(Optional.of(proyectoFalso));

        // --- ACT (Actuar) ---
        Project resultado = projectService.getProject(100);

        // --- ASSERT (Verificar) ---
        assertNotNull(resultado);
        assertEquals(100, resultado.getId());
        assertEquals("Proyecto de Prueba", resultado.getNameProject());

        // Verificamos que se llamó 2 veces, como descubrimos antes
        verify(projectRepository, times(2)).findById(100);
    }
}