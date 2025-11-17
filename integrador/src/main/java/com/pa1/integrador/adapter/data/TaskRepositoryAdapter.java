package com.pa1.integrador.adapter.data;

import com.pa1.integrador.adapter.data.jpa.repository.ITaskJpaRepository;
import com.pa1.integrador.adapter.data.mapper.PersistenceMapper;
import com.pa1.integrador.core.model.Task;
import com.pa1.integrador.core.model.port.output.IPuertoRepoTareas;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TaskRepositoryAdapter implements IPuertoRepoTareas {

    private final ITaskJpaRepository jpaRepository;
    private final PersistenceMapper mapper;

    public TaskRepositoryAdapter(ITaskJpaRepository jpaRepository, PersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Task> findById(Long id) {
        return jpaRepository.findById(id)
                .map(mapper::taskEntityToTask);
    }

    @Override
    public List<Task> findAllByProjectId(Long projectId) {

        var entities = jpaRepository.findAllByProjectId(projectId);

        return mapper.taskEntityListToTaskList(entities);
    }

    @Override
    public Task save(Task task) {
        var entity = mapper.taskToTaskEntity(task);
        var savedEntity = jpaRepository.save(entity);
        return mapper.taskEntityToTask(savedEntity);
    }
}