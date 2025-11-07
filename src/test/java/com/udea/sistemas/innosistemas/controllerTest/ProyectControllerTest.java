package com.udea.sistemas.innosistemas.controllerTest;

// --- Imports de Mockito ---
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

// --- Imports de Spring y DTOs ---
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.udea.sistemas.innosistemas.controllers.ProyectController;
import com.udea.sistemas.innosistemas.models.dto.ApiResponseDto;
import com.udea.sistemas.innosistemas.models.dto.CreateProjectDto;
import com.udea.sistemas.innosistemas.models.entity.Project;
import com.udea.sistemas.innosistemas.repository.ProjectRepository;
import com.udea.sistemas.innosistemas.service.ProjectService;

// --- Imports de AssertJ y Mockito (static) ---
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // Se usa en lugar de @WebMvcTest
class ProyectControllerTest {

    // --- Se usa @Mock en lugar de @MockBean ---
    @Mock
    private ProjectService proyectService;

    @Mock
    private ProjectRepository projectRepository;

    // --- Se usa @InjectMocks para inyectar los @Mock en el controlador ---
    @InjectMocks
    private ProyectController proyectController;

    // --- NOTA: No hay MockMvc ni ObjectMapper ---

    @Test
        // --- NOTA: No hay @WithMockUser. La seguridad no se prueba ---
    void testGetProject_Success() throws Exception {
        // 1. Arrange
        Project mockProject = new Project();
        mockProject.setId(1);
        mockProject.setNameProject("Proyecto de Prueba");

        when(proyectService.getProject(1)).thenReturn(mockProject);

        // 2. Act
        // Se llama al método directamente, no a través de una petición HTTP
        ResponseEntity<Project> response = proyectController.getProject(1);

        // 3. Assert
        // Se comprueba el ResponseEntity
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getId());
        assertEquals("Proyecto de Prueba", response.getBody().getNameProject());
    }

    @Test
    void testGetProject_NotFound() throws Exception {
        // 1. Arrange
        when(proyectService.getProject(99)).thenReturn(null);

        // 2. Act
        ResponseEntity<Project> response = proyectController.getProject(99);

        // 3. Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
        // --- NOTA: La anotación @PreAuthorize("hasAuthority('create_project')") es ignorada ---
    void testCreateProject_Success() throws Exception {
        // 1. Arrange
        CreateProjectDto projectDto = new CreateProjectDto("Nuevo Proyecto", "Desc...", 101);
        when(proyectService.createProject(anyInt(), anyString(), anyString())).thenReturn(true);

        // 2. Act
        ResponseEntity<ApiResponseDto> response = proyectController.createProject(projectDto);

        // 3. Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(true, response.getBody().success());
        assertEquals("Project was created successfully", response.getBody().message());
    }

    @Test
    void testCreateProject_Fail() throws Exception {
        // 1. Arrange
        CreateProjectDto projectDto = new CreateProjectDto("Proyecto Malo", "Desc...", 101);
        when(proyectService.createProject(anyInt(), anyString(), anyString())).thenReturn(false);

        // 2. Act
        ResponseEntity<ApiResponseDto> response = proyectController.createProject(projectDto);

        // 3. Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(false, response.getBody().success());
        assertEquals("Invalid Project data", response.getBody().message());
    }
}