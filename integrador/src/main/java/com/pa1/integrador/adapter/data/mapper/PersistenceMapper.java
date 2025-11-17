package com.pa1.integrador.adapter.data.mapper;

import com.pa1.integrador.adapter.data.jpa.entity.ProjectEntity;
import com.pa1.integrador.adapter.data.jpa.entity.TaskEntity;
import com.pa1.integrador.core.model.Project;
import com.pa1.integrador.core.model.Task;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PersistenceMapper {


    public Project projectEntityToProject(ProjectEntity entity) {
        if (entity == null) return null;

        return Project.of(
                entity.getId(),
                entity.getName(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getStatus(),
                entity.getDescription()
        );
    }

    public ProjectEntity projectToProjectEntity(Project project) {
        if (project == null) return null;

        ProjectEntity entity = new ProjectEntity();
        entity.setId(project.getId());
        entity.setName(project.getName());
        entity.setStartDate(project.getStartDate());
        entity.setEndDate(project.getEndDate());
        entity.setStatus(project.getStatus());
        entity.setDescription(project.getDescription());
        return entity;
    }

    public Task taskEntityToTask(TaskEntity entity) {
        if (entity == null) return null;

        return Task.of(
                entity.getId(),
                projectEntityToProject(entity.getProject()),
                entity.getTitle(),
                entity.getEstimateHours(),
                entity.getAssignee(),
                entity.getStatus(),
                entity.getFinishedAt(),
                entity.getCreatedAt()
        );
    }

    public TaskEntity taskToTaskEntity(Task task) {
        if (task == null) return null;

        TaskEntity entity = new TaskEntity();
        entity.setId(task.getId());
        entity.setTitle(task.getTitle());
        entity.setEstimateHours(task.getEstimateHours());
        entity.setAssignee(task.getAssignee());
        entity.setStatus(task.getStatus());
        entity.setFinishedAt(task.getFinishedAt());
        entity.setCreatedAt(task.getCreatedAt());

        entity.setProject(projectToProjectEntity(task.getProject()));
        return entity;
    }

    public List<Task> taskEntityListToTaskList(List<TaskEntity> entities) {
        return entities.stream()
                .map(this::taskEntityToTask)
                .collect(Collectors.toList());
    }
}