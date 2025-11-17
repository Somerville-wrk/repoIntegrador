package com.pa1.integrador.core.model.port.output;

import com.pa1.integrador.core.model.Task;
import java.util.List;
import java.util.Optional;

public interface IPuertoRepoTareas {

    Optional<Task> findById(Long id);

    List<Task> findAllByProjectId(Long projectId);

    Task save(Task task);
}
