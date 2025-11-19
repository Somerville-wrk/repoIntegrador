package com.pa1.integrador.core.model;
import com.pa1.integrador.core.exception.ViolacionReglaDeNegocioExcepcion;
import java.time.LocalDateTime;
import java.util.Objects;


public class Task {

    private Long id;
    private final Project project;
    private final String title;
    private final Integer estimateHours;
    private final String assignee;
    private TaskStatus status;
    private LocalDateTime finishedAt;
    private final LocalDateTime createdAt;


    private Task(Long id, Project project, String title, Integer estimateHours, String assignee, TaskStatus status, LocalDateTime finishedAt, LocalDateTime createdAt) {
        this.id = id;
        this.project = project;
        this.title = title;
        this.estimateHours = estimateHours;
        this.assignee = assignee;
        this.status = status;
        this.finishedAt = finishedAt;
        this.createdAt = createdAt;
    }



    public static Task create(Project project, String title, Integer estimateHours, String assignee, TaskStatus status, LocalDateTime createdAt) {


        Objects.requireNonNull(project, "El proyecto no puede ser nulo");
        Objects.requireNonNull(title, "El título no puede ser nulo");


        if (estimateHours == null || estimateHours <= 0) {
            throw new ViolacionReglaDeNegocioExcepcion("La estimación de horas debe ser un valor positivo.");
        }

        LocalDateTime finished = (status == TaskStatus.DONE) ? createdAt : null;

        return new Task(null, project, title, estimateHours, assignee, status, finished, createdAt);
    }


    public static Task of(Long id, Project project, String title, Integer estimateHours, String assignee, TaskStatus status, LocalDateTime finishedAt, LocalDateTime createdAt) {
        return new Task(id, project, title, estimateHours, assignee, status, finishedAt, createdAt);
    }



    public void changeStatus(TaskStatus newStatus, LocalDateTime timestamp) {
        Objects.requireNonNull(newStatus, "El nuevo estado no puede ser nulo");


        if (newStatus == TaskStatus.DONE && this.status != TaskStatus.DONE) {
            this.finishedAt = timestamp;
        }

        else if (newStatus != TaskStatus.DONE) {
            this.finishedAt = null;
        }

        this.status = newStatus;
    }




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        if (this.id != null) {
            // throw new IllegalStateException("El ID de la tarea ya está asignado.");
        }
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

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

    public LocalDateTime getFinishedAt() {
        return finishedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}