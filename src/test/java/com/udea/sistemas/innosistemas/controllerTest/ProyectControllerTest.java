package com.udea.sistemas.innosistemas.controllerTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.udea.sistemas.innosistemas.controllers.ProyectController;
import com.udea.sistemas.innosistemas.models.dto.ApiResponseDto;
import com.udea.sistemas.innosistemas.models.dto.CreateProjectDto;
import com.udea.sistemas.innosistemas.models.dto.ProjectDto;
import com.udea.sistemas.innosistemas.models.dto.modProjectDto;
import com.udea.sistemas.innosistemas.models.entity.Project;
import com.udea.sistemas.innosistemas.models.entity.User;
import com.udea.sistemas.innosistemas.repository.ProjectRepository;
import com.udea.sistemas.innosistemas.service.ProjectService;

import java.util.List;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProyectControllerTest {

    @Mock
    private ProjectService proyectService;

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProyectController proyectController;

    // --- Pruebas de cobertura de lógica principal (77.8%) ---

    @Test
    void testGetProject_Success() throws Exception {
        Project mockProject = new Project();
        mockProject.setId(1);
        mockProject.setNameProject("Proyecto de Prueba");

        when(proyectService.getProject(1)).thenReturn(mockProject);
        ResponseEntity<Project> response = proyectController.getProject(1);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getId());
    }

    @Test
    void testGetProject_NotFound() throws Exception {
        when(proyectService.getProject(99)).thenReturn(null);
        ResponseEntity<Project> response = proyectController.getProject(99);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testCreateProject_Success() throws Exception {
        CreateProjectDto projectDto = new CreateProjectDto("Nuevo Proyecto", "Desc...", 101);
        when(proyectService.createProject(anyInt(), anyString(), anyString())).thenReturn(true);
        ResponseEntity<ApiResponseDto> response = proyectController.createProject(projectDto);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(true, response.getBody().success());
    }

    @Test
    void testCreateProject_Fail() throws Exception {
        CreateProjectDto projectDto = new CreateProjectDto("Proyecto Malo", "Desc...", 101);
        when(proyectService.createProject(anyInt(), anyString(), anyString())).thenReturn(false);
        ResponseEntity<ApiResponseDto> response = proyectController.createProject(projectDto);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(false, response.getBody().success());
    }

    @Test
    void testGetUsersInOneTeam() {
        // Arrange
        User user1 = new User();
        user1.setEmail("test@test.com");
        List<User> userList = List.of(user1);
        when(proyectService.getUsersInOneTeamByProject(1)).thenReturn(userList);

        // Act
        ResponseEntity<List<User>> response = proyectController.getUsersInOneTeam(1);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("test@test.com", response.getBody().get(0).getEmail());
    }

    @Test
    void testGetAllProjects_Success() {
        // Arrange
        Project project1 = new Project();
        project1.setId(1);
        project1.setNameProject("P1");
        List<Project> projectList = List.of(project1);

        when(projectRepository.findAll()).thenReturn(projectList);

        // Act
        ResponseEntity<List<ProjectDto>> response = proyectController.getAllProjects();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("P1", response.getBody().get(0).name());
    }

    @Test
    void testGetAllProjects_Empty() {
        // Arrange
        when(projectRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<List<ProjectDto>> response = proyectController.getAllProjects();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testUpdateProject_Success() {
        // Arrange
        modProjectDto dto = new modProjectDto(1, 101, "Actualizado", "Desc...");
        when(proyectService.updateProject(1, 101, "Actualizado", "Desc...")).thenReturn(true);

        // Act
        ResponseEntity<ApiResponseDto> response = proyectController.updateProject(dto);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody().success());
        assertEquals("Project was updated successfully", response.getBody().message());
    }

    @Test
    void testUpdateProject_Fail() {
        // Arrange
        modProjectDto dto = new modProjectDto(1, 101, "Actualizado", "Desc...");
        when(proyectService.updateProject(anyInt(), anyInt(), anyString(), anyString())).thenReturn(false);

        // Act
        ResponseEntity<ApiResponseDto> response = proyectController.updateProject(dto);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(false, response.getBody().success());
        assertEquals("Invalid Project data", response.getBody().message());
    }

    @Test
    void testDeleteProject_Success() {
        // Arrange
        when(proyectService.deleteProject(1)).thenReturn(true);

        // Act
        ResponseEntity<ApiResponseDto> response = proyectController.deleteProject(1);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody().success());
    }

    @Test
    void testDeleteProject_Fail() {
        // Arrange
        when(proyectService.deleteProject(99)).thenReturn(false);

        // Act
        ResponseEntity<ApiResponseDto> response = proyectController.deleteProject(99);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(false, response.getBody().success());
    }

    @Test
    void testInvalidateProject_Success() {
        // Arrange
        when(proyectService.invalidateProject(1)).thenReturn(true);

        // Act
        ResponseEntity<ApiResponseDto> response = proyectController.invalidateProject(1);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody().success());
    }

    @Test
    void testInvalidateProject_Fail() {
        // Arrange
        when(proyectService.invalidateProject(99)).thenReturn(false);

        // Act
        ResponseEntity<ApiResponseDto> response = proyectController.invalidateProject(99);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(false, response.getBody().success());
    }

    // --- INICIO DE PRUEBAS DE EXCEPCIÓN (Para > 80%) ---

    @Test
    void testGetAllProjects_Exception() {
        // Arrange
        // Simula un error de base de datos
        when(projectRepository.findAll()).thenThrow(new RuntimeException("Error simulado de BD"));

        // Act
        ResponseEntity<List<ProjectDto>> response = proyectController.getAllProjects();

        // Assert
        // Verifica que el catch funcionó y devolvió un INTERNAL_SERVER_ERROR
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testCreateProject_Exception() {
        // Arrange
        CreateProjectDto projectDto = new CreateProjectDto("Proyecto Excepcion", "Desc...", 101);

        // Simula un error inesperado en la lógica del servicio
        when(proyectService.createProject(anyInt(), anyString(), anyString()))
                .thenThrow(new RuntimeException("Error simulado de servicio"));

        // Act
        ResponseEntity<ApiResponseDto> response = proyectController.createProject(projectDto);

        // Assert
        // Verifica que el catch funcionó y devolvió el DTO de error
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(false, response.getBody().success());
        assertEquals("Error creating project: Error simulado de servicio", response.getBody().message());
    }

    @Test
    void testGetProject_Exception() {
        // Arrange
        // Simula un error al buscar un proyecto
        when(proyectService.getProject(1)).thenThrow(new RuntimeException("Error simulado de servicio"));

        // Act
        ResponseEntity<Project> response = proyectController.getProject(1);

        // Assert
        // Verifica que el catch funcionó
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}