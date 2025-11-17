package com.pa1.integrador.adapter.service.controller;

import com.pa1.integrador.adapter.service.dto.ProjectResponseDTO;
import com.pa1.integrador.adapter.service.dto.TaskResponseDTO;
import com.pa1.integrador.adapter.service.dto.UpdateStatusRequestDTO;
import com.pa1.integrador.adapter.service.mapper.ApiMapper;
import com.pa1.integrador.core.model.port.input.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {


    private final IEncontrarProyectoPorID findProjectByIdUseCase;
    private final IActualizarEstadoProyecto updateProjectStatusUseCase;
    private final IEncontrarTodasLasTareasPorIdProyecto findAllTasksByProjectIdUseCase;
    private final IActualizarEstadoTarea updateTaskStatusUseCase;
    private final ApiMapper mapper;


    public ProjectController(
            IEncontrarProyectoPorID findProjectByIdUseCase,
            IActualizarEstadoProyecto updateProjectStatusUseCase,
            IEncontrarTodasLasTareasPorIdProyecto findAllTasksByProjectIdUseCase,
            IActualizarEstadoTarea updateTaskStatusUseCase,
            ApiMapper mapper) {

        this.findProjectByIdUseCase = findProjectByIdUseCase;
        this.updateProjectStatusUseCase = updateProjectStatusUseCase;
        this.findAllTasksByProjectIdUseCase = findAllTasksByProjectIdUseCase;
        this.updateTaskStatusUseCase = updateTaskStatusUseCase;
        this.mapper = mapper;
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectResponseDTO> findProjectById(@PathVariable Long projectId) {

        return findProjectByIdUseCase.findById(projectId)
                .map(mapper::projectToProjectResponseDTO)
                .map(ResponseEntity::ok) // Si lo encuentra, devuelve 200 OK
                .orElseGet(() -> ResponseEntity.notFound().build()); // Si no, devuelve 404 NOT FOUND
    }


    @GetMapping("/{projectId}/tasks")
    public ResponseEntity<List<TaskResponseDTO>> findAllTasksByProjectId(@PathVariable Long projectId) {

        List<TaskResponseDTO> dtoList = findAllTasksByProjectIdUseCase.findAllByProjectId(projectId)
                .stream()
                .map(mapper::taskToTaskResponseDTO)
                .collect(java.util.stream.Collectors.toList());

        return ResponseEntity.ok(dtoList);
    }

    @PatchMapping("/{projectId}/status")
    public ResponseEntity<ProjectResponseDTO> updateProjectStatus(
            @PathVariable Long projectId,
            @RequestBody UpdateStatusRequestDTO request) {

        if (request.getProjectStatus() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        var updatedProject = updateProjectStatusUseCase.updateStatus(
                projectId,
                request.getProjectStatus());

        return ResponseEntity.ok(mapper.projectToProjectResponseDTO(updatedProject));
    }

    @PatchMapping("/{projectId}/tasks/{taskId}/status")
    public ResponseEntity<TaskResponseDTO> updateTaskStatus(
            @PathVariable Long projectId, // Usado solo para validación en la URL
            @PathVariable Long taskId,
            @RequestBody UpdateStatusRequestDTO request) {

        if (request.getTaskStatus() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }


        var updatedTask = updateTaskStatusUseCase.updateStatus(
                taskId,
                request.getTaskStatus());

        // Devuelve 200 OK y el recurso actualizado
        return ResponseEntity.ok(mapper.taskToTaskResponseDTO(updatedTask));
    }
}