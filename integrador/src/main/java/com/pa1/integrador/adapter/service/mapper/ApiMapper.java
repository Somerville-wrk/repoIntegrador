package com.pa1.integrador.adapter.service.mapper;

import com.pa1.integrador.adapter.service.dto.ProjectResponseDTO;
import com.pa1.integrador.adapter.service.dto.TaskResponseDTO;
import com.pa1.integrador.core.model.Project;
import com.pa1.integrador.core.model.Task;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ApiMapper {


    public ProjectResponseDTO projectToProjectResponseDTO(Project project) {
        if (project == null) return null;

        ProjectResponseDTO dto = new ProjectResponseDTO();
        dto.setId(project.getId());
        dto.setName(project.getName());
        dto.setStartDate(project.getStartDate());
        dto.setEndDate(project.getEndDate());
        dto.setStatus(project.getStatus());
        dto.setDescription(project.getDescription());
        return dto;
    }

    public TaskResponseDTO taskToTaskResponseDTO(Task task) {
        if (task == null) return null;

        TaskResponseDTO dto = new TaskResponseDTO();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setEstimateHours(task.getEstimateHours());
        dto.setAssignee(task.getAssignee());
        dto.setStatus(task.getStatus());
        dto.setCreatedAt(task.getCreatedAt());
        dto.setFinishedAt(task.getFinishedAt());


        if (task.getProject() != null) {
            dto.setProjectId(task.getProject().getId());
        }

        return dto;
    }


    public List<TaskResponseDTO> taskListToTaskResponseDTOList(List<Task> tasks) {
        return tasks.stream()
                .map(this::taskToTaskResponseDTO)
                .collect(Collectors.toList());
    }
}