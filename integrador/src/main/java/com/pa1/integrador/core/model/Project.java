package com.pa1.integrador.core.model;

import java.time.LocalDate;
import java.util.Objects;


public class Project {

    private Long id;
    private final String name;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private ProjectStatus status;
    private final String description;


    private Project(Long id, String name, LocalDate startDate, LocalDate endDate, ProjectStatus status, String description) {

        Objects.requireNonNull(name, "El nombre no puede ser nulo");
        Objects.requireNonNull(startDate, "La fecha de inicio no puede ser nula");
        Objects.requireNonNull(endDate, "La fecha de fin no puede ser nula");

        if (endDate.isBefore(startDate)) {
            // En un caso real, lanzaríamos una excepción de dominio
            // throw new BusinessRuleViolationException("La fecha de fin no puede ser anterior a la de inicio");
        }

        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.description = description;
    }


    public static Project create(String name, LocalDate startDate, LocalDate endDate, ProjectStatus status, String description) {

        return new Project(null, name, startDate, endDate, status, description);
    }


    public static Project of(Long id, String name, LocalDate startDate, LocalDate endDate, ProjectStatus status, String description) {
        return new Project(id, name, startDate, endDate, status, description);
    }


    public void changeStatus(ProjectStatus newStatus) {
        Objects.requireNonNull(newStatus, "El nuevo estado no puede ser nulo");


        if (this.status == ProjectStatus.CLOSED && newStatus == ProjectStatus.ACTIVE) {
            // throw new BusinessRuleViolationException("Un proyecto cerrado no puede reactivarse");
        }

        this.status = newStatus;
    }

    public boolean isClosed() {
        return this.status == ProjectStatus.CLOSED;
    }



    public Long getId() {
        return id;
    }

    // Necesitamos este "setter" especial solo para que el repositorio
    // pueda asignar el ID después de guardarlo por primera vez.
    public void setId(Long id) {
        if (this.id != null) {
            // throw new IllegalStateException("El ID del proyecto ya está asignado.");
        }
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }
}