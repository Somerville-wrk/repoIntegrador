package com.pa1.integrador.core.usecase;

import com.pa1.integrador.core.model.Task;
import com.pa1.integrador.core.model.port.input.IEncontrarTodasLasTareasPorIdProyecto;
import com.pa1.integrador.core.model.port.output.IPuertoRepoTareas;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindAllTasksByProjectIdUseCase implements IEncontrarTodasLasTareasPorIdProyecto {

    private final IPuertoRepoTareas taskRepository;

    public FindAllTasksByProjectIdUseCase(IPuertoRepoTareas taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Task> findAllByProjectId(Long projectId) {
        return taskRepository.findAllByProjectId(projectId);
    }
}