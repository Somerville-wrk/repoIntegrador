package com.pa1.integrador.adapter.service.controller;

import com.pa1.integrador.core.model.Task;
import com.pa1.integrador.adapter.service.dto.CreateProjectRequestDTO;
import com.pa1.integrador.adapter.service.dto.ProjectResponseDTO;
import com.pa1.integrador.adapter.service.dto.TaskResponseDTO;
import com.pa1.integrador.adapter.service.dto.UpdateStatusRequestDTO;
import com.pa1.integrador.adapter.service.dto.TaskCreationRequest;
import com.pa1.integrador.adapter.service.mapper.ApiMapper;

import com.pa1.integrador.core.model.port.input.IActualizarEstadoProyecto;
import com.pa1.integrador.core.model.port.input.IActualizarEstadoTarea;
import com.pa1.integrador.core.model.port.input.ICrearProyecto;
import com.pa1.integrador.core.model.port.input.ICrearTarea;
import com.pa1.integrador.core.model.port.input.IEncontrarProyectoPorID;
import com.pa1.integrador.core.model.port.input.IEncontrarTodasLasTareasPorIdProyecto;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/projects")
public class ProjectController {


    private final ICrearProyecto createProjectUseCase;
    private final IEncontrarProyectoPorID findProjectByIdUseCase;
    private final IActualizarEstadoProyecto updateProjectStatusUseCase;
    private final IEncontrarTodasLasTareasPorIdProyecto findAllTasksByProjectIdUseCase;
    private final IActualizarEstadoTarea updateTaskStatusUseCase;
    private final ApiMapper mapper;
    private final ICrearTarea createTaskUseCase;


    public ProjectController(
            ICrearProyecto createProjectUseCase,
            IEncontrarProyectoPorID findProjectByIdUseCase,
            IActualizarEstadoProyecto updateProjectStatusUseCase,
            IEncontrarTodasLasTareasPorIdProyecto findAllTasksByProjectIdUseCase,
            IActualizarEstadoTarea updateTaskStatusUseCase,
            ApiMapper mapper,
            ICrearTarea createTaskUseCase) {

        this.createProjectUseCase = createProjectUseCase;
        this.findProjectByIdUseCase = findProjectByIdUseCase;
        this.updateProjectStatusUseCase = updateProjectStatusUseCase;
        this.findAllTasksByProjectIdUseCase = findAllTasksByProjectIdUseCase;
        this.updateTaskStatusUseCase = updateTaskStatusUseCase;
        this.mapper = mapper;
        this.createTaskUseCase = createTaskUseCase;
    }


    @PostMapping
    public ResponseEntity<ProjectResponseDTO> createProject(
            @RequestBody @Valid CreateProjectRequestDTO request) {

        var newProject = createProjectUseCase.createProject(
                request.getName(),
                request.getStartDate(),
                request.getEndDate(),
                request.getStatus(),
                request.getDescription()
        );

        return new ResponseEntity<>(
                mapper.projectToProjectResponseDTO(newProject),
                HttpStatus.CREATED
        );
    }


    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectResponseDTO> findProjectById(@PathVariable Long projectId) {


        return findProjectByIdUseCase.findById(projectId)
                .map(mapper::projectToProjectResponseDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping("/{projectId}/tasks")
    public ResponseEntity<List<TaskResponseDTO>> findAllTasksByProjectId(@PathVariable Long projectId) {


        List<TaskResponseDTO> dtoList = findAllTasksByProjectIdUseCase.findAllByProjectId(projectId)
                .stream()
                .map(mapper::taskToTaskResponseDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtoList);
    }


    @PatchMapping("/{projectId}/status")
    public ResponseEntity<ProjectResponseDTO> updateProjectStatus(
            @PathVariable Long projectId,
            @RequestBody UpdateStatusRequestDTO request) {

        if (request.getProjectStatus() == null) {
            return ResponseEntity.badRequest().build();
        }


        var updatedProject = updateProjectStatusUseCase.updateStatus(
                projectId,
                request.getProjectStatus());

        return ResponseEntity.ok(mapper.projectToProjectResponseDTO(updatedProject));
    }


    @PatchMapping("/{projectId}/tasks/{taskId}/status")
    public ResponseEntity<TaskResponseDTO> updateTaskStatus(
            @PathVariable Long projectId,
            @PathVariable Long taskId,
            @RequestBody UpdateStatusRequestDTO request) {

        if (request.getTaskStatus() == null) {
            return ResponseEntity.badRequest().build();
        }


        var updatedTask = updateTaskStatusUseCase.updateStatus(
                taskId,
                request.getTaskStatus());

        return ResponseEntity.ok(mapper.taskToTaskResponseDTO(updatedTask));
    }


    @PostMapping("/{projectId}/tasks")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponseDTO createTask(
            @PathVariable Long projectId,
            @Valid @RequestBody TaskCreationRequest request) {

        Task createdTask = createTaskUseCase.createTask(
                projectId,
                request.getTitle(),
                request.getEstimateHours(),
                request.getAssignee(),
                request.getStatus()
        );

        return mapper.taskToTaskResponseDTO(createdTask);
    }
}