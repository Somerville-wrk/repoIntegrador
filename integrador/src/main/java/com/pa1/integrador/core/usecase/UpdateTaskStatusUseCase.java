package com.pa1.integrador.core.usecase;

import com.pa1.integrador.core.exception.RecursoNoEncontradoExcepcion;
import com.pa1.integrador.core.model.Task;
import com.pa1.integrador.core.model.TaskStatus;
import com.pa1.integrador.core.model.port.input.IActualizarEstadoTarea;
import com.pa1.integrador.core.model.port.output.IPuertoRepoTareas;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;

@Service
public class UpdateTaskStatusUseCase implements IActualizarEstadoTarea {

    private final IPuertoRepoTareas taskRepository;
    private final Clock clock;

    public UpdateTaskStatusUseCase(IPuertoRepoTareas taskRepository, Clock clock) {
        this.taskRepository = taskRepository;
        this.clock = clock;
    }

    @Override
    public Task updateStatus(Long taskId, TaskStatus newStatus) {
        // 1. Buscar la tarea
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RecursoNoEncontradoExcepcion("Tarea no encontrada con id: " + taskId));

        LocalDateTime now = LocalDateTime.now(clock);

        task.changeStatus(newStatus, now);

        return taskRepository.save(task);
    }
}