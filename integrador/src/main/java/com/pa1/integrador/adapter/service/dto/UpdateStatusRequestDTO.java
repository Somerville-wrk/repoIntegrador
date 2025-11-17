package com.pa1.integrador.adapter.service.dto;

import com.pa1.integrador.core.model.ProjectStatus;
import com.pa1.integrador.core.model.TaskStatus;


public class UpdateStatusRequestDTO {

    private ProjectStatus projectStatus;
    private TaskStatus taskStatus;

    // Getters y Setters (necesarios para que [JSON] funcione)
    public ProjectStatus getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(ProjectStatus projectStatus) {
        this.projectStatus = projectStatus;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }
}