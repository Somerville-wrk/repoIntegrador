package com.pa1.integrador.core.usecase;

import com.pa1.integrador.core.exception.RecursoNoEncontradoExcepcion;
import com.pa1.integrador.core.exception.ViolacionReglaDeNegocioExcepcion;
import com.pa1.integrador.core.model.Project;
import com.pa1.integrador.core.model.ProjectStatus;
import com.pa1.integrador.core.model.Task;
import com.pa1.integrador.core.model.TaskStatus;
import com.pa1.integrador.core.model.port.input.ICrearTarea;
import com.pa1.integrador.core.model.port.output.IPuertoRepoProyecto;
import com.pa1.integrador.core.model.port.output.IPuertoRepoTareas;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;

@Service
public class CreateTaskUseCase implements ICrearTarea {

    private final IPuertoRepoProyecto projectRepository;
    private final IPuertoRepoTareas taskRepository;
    private final Clock clock;

    public CreateTaskUseCase(IPuertoRepoProyecto projectRepository, IPuertoRepoTareas taskRepository, Clock clock) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.clock = clock;
    }

    @Override
    public Task createTask(Long projectId, String title, Integer estimateHours, String assignee, TaskStatus status)
            throws RecursoNoEncontradoExcepcion, ViolacionReglaDeNegocioExcepcion {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RecursoNoEncontradoExcepcion("Proyecto con ID " + projectId + " no encontrado."));


        if (project.getStatus() == ProjectStatus.CLOSED) {
            throw new ViolacionReglaDeNegocioExcepcion("No se pueden agregar tareas a un proyecto que está CERRADO.");
        }

        Task newTask = Task.create(
                project,
                title,
                estimateHours,
                assignee,
                status,
                LocalDateTime.now(clock)
        );

        return taskRepository.save(newTask);
    }
}