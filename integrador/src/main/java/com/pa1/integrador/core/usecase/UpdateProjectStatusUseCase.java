package com.pa1.integrador.core.usecase;

import com.pa1.integrador.core.exception.ViolacionReglaDeNegocioExcepcion;
import com.pa1.integrador.core.exception.RecursoNoEncontradoExcepcion;
import com.pa1.integrador.core.model.Project;
import com.pa1.integrador.core.model.ProjectStatus;
import com.pa1.integrador.core.model.port.input.IActualizarEstadoProyecto;
import com.pa1.integrador.core.model.port.output.IPuertoRepoProyecto;
import com.pa1.integrador.core.model.port.output.IPuertoRepoTareas;
import org.springframework.stereotype.Service;

@Service
public class UpdateProjectStatusUseCase implements IActualizarEstadoProyecto {

    private final IPuertoRepoProyecto projectRepository;
    private final IPuertoRepoTareas taskRepository;

    public UpdateProjectStatusUseCase(IPuertoRepoProyecto projectRepository, IPuertoRepoTareas taskRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public Project updateStatus(Long projectId, ProjectStatus newStatus) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RecursoNoEncontradoExcepcion("Proyecto no encontrado con id: " + projectId));

        if (newStatus == ProjectStatus.CLOSED) {
            long tasksInProgress = taskRepository.findAllByProjectId(projectId).stream()
                    .filter(task -> task.getStatus() == com.pa1.integrador.core.model.TaskStatus.IN_PROGRESS)
                    .count();

            if (tasksInProgress > 0) {
                throw new ViolacionReglaDeNegocioExcepcion("El proyecto no puede cerrarse, tiene tareas 'IN_PROGRESS'.");
            }
        }

        project.changeStatus(newStatus);

        return projectRepository.save(project);
    }
}