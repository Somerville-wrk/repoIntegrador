package com.pa1.integrador.adapter.service.dto;

import com.pa1.integrador.core.model.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class TaskCreationRequest {

    @NotBlank(message = "El título de la tarea no puede estar vacío.")
    private String title;

    @NotNull(message = "La estimación de horas es requerida.")
    @Positive(message = "La estimación de horas debe ser un valor positivo.")
    private Integer estimateHours;

    @NotBlank(message = "El responsable (assignee) es requerido.")
    private String assignee;

    @NotNull(message = "El estado inicial de la tarea es requerido.")
    private TaskStatus status;


    public String getTitle() {
        return title;
    }

    public Integer getEstimateHours() {
        return estimateHours;
    }

    public String getAssignee() {
        return assignee;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setEstimateHours(Integer estimateHours) {
        this.estimateHours = estimateHours;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }
}