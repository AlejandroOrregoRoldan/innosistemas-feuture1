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

    // --- Pruebas que ya teníamos ---

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

    // --- INICIO DE PRUEBAS NUEVAS (Para subir cobertura) ---

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

        // El controlador llama a projectRepository.findAll()
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
        // Simula que la base de datos no devuelve nada
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
        // Simula que el servicio actualizó correctamente
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
        // Simula que el servicio falló (ej. no encontró el ID)
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
}