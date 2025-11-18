package com.pa1.integrador.adapter.service.controller;


import com.pa1.integrador.adapter.service.dto.CreateProjectRequestDTO;
import com.pa1.integrador.adapter.service.dto.ProjectResponseDTO;
import com.pa1.integrador.adapter.service.dto.TaskResponseDTO;
import com.pa1.integrador.adapter.service.dto.UpdateStatusRequestDTO;
import com.pa1.integrador.adapter.service.mapper.ApiMapper;
import com.pa1.integrador.core.model.port.input.*;
import com.pa1.integrador.core.model.ProjectStatus;
import com.pa1.integrador.core.model.TaskStatus;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ICrearProyecto createProjectUseCase;
    private final IEncontrarProyectoPorID findProjectByIdUseCase;
    private final IActualizarEstadoProyecto updateProjectStatusUseCase;
    private final IEncontrarTodasLasTareasPorIdProyecto findAllTasksByProjectIdUseCase;
    private final IActualizarEstadoTarea updateTaskStatusUseCase;
    private final ApiMapper mapper;

    // INYECCIÓN DE DEPENDENCIAS
    public ProjectController(
            ICrearProyecto createProjectUseCase,
            IEncontrarProyectoPorID findProjectByIdUseCase,
            IActualizarEstadoProyecto updateProjectStatusUseCase,
            IEncontrarTodasLasTareasPorIdProyecto findAllTasksByProjectIdUseCase,
            IActualizarEstadoTarea updateTaskStatusUseCase,
            ApiMapper mapper) {

        this.createProjectUseCase = createProjectUseCase;
        this.findProjectByIdUseCase = findProjectByIdUseCase;
        this.updateProjectStatusUseCase = updateProjectStatusUseCase;
        this.findAllTasksByProjectIdUseCase = findAllTasksByProjectIdUseCase;
        this.updateTaskStatusUseCase = updateTaskStatusUseCase;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<ProjectResponseDTO> createProject(
            @RequestBody @Valid CreateProjectRequestDTO request) {

        // Llamada al Caso de Uso
        var newProject = createProjectUseCase.createProject(
                request.getName(),
                request.getStartDate(),
                request.getEndDate(),
                request.getStatus(),
                request.getDescription()
        );

        // Respuesta 201 CREATED
        return new ResponseEntity<>(
                mapper.projectToProjectResponseDTO(newProject),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectResponseDTO> findProjectById(@PathVariable Long projectId) {


        return findProjectByIdUseCase.findById(projectId)
                .map(mapper::projectToProjectResponseDTO)
                .map(ResponseEntity::ok) // 200 OK
                .orElseGet(() -> ResponseEntity.notFound().build()); // 404 NOT FOUND
    }

    @GetMapping("/{projectId}/tasks")
    public ResponseEntity<List<TaskResponseDTO>> findAllTasksByProjectId(@PathVariable Long projectId) {


        List<TaskResponseDTO> dtoList = findAllTasksByProjectIdUseCase.findAllByProjectId(projectId)
                .stream()
                .map(mapper::taskToTaskResponseDTO)
                .collect(java.util.stream.Collectors.toList());

        return ResponseEntity.ok(dtoList); // 200 OK
    }

    @PatchMapping("/{projectId}/status")
    public ResponseEntity<ProjectResponseDTO> updateProjectStatus(
            @PathVariable Long projectId,
            @RequestBody UpdateStatusRequestDTO request) {


        if (request.getProjectStatus() == null) {
            return ResponseEntity.badRequest().build(); // 400 BAD REQUEST
        }

        // Llama al Use Case (lanza 404 si no existe, 409 si falla la regla)
        var updatedProject = updateProjectStatusUseCase.updateStatus(
                projectId,
                request.getProjectStatus());

        return ResponseEntity.ok(mapper.projectToProjectResponseDTO(updatedProject)); // 200 OK
    }

    @PatchMapping("/{projectId}/tasks/{taskId}/status")
    public ResponseEntity<TaskResponseDTO> updateTaskStatus(
            @PathVariable Long projectId,
            @PathVariable Long taskId,
            @RequestBody UpdateStatusRequestDTO request) {


        if (request.getTaskStatus() == null) {
            return ResponseEntity.badRequest().build(); // 400 BAD REQUEST
        }

        // Llama al Use Case (lanza 404 si no existe, y maneja finishedAt)
        var updatedTask = updateTaskStatusUseCase.updateStatus(
                taskId,
                request.getTaskStatus());

        return ResponseEntity.ok(mapper.taskToTaskResponseDTO(updatedTask)); // 200 OK
    }
}