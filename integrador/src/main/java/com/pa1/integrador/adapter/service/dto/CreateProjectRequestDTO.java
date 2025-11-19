package com.pa1.integrador.adapter.service.dto;

import com.pa1.integrador.core.model.ProjectStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class CreateProjectRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate startDate;

    @NotNull(message = "La fecha de fin es obligatoria")
    private LocalDate endDate;

    @NotNull(message = "El estado es obligatorio")
    private ProjectStatus status;

    private String description;

    public String getName() {
        return name; }
    public void setName(String name) {
        this.name = name; }
    public LocalDate getStartDate() {
        return startDate; }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate; }
    public LocalDate getEndDate() {
        return endDate; }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    public ProjectStatus getStatus() {
        return status; }
    public void setStatus(ProjectStatus status) {
        this.status = status; }
    public String getDescription() {
        return description; }
    public void setDescription(String description) {
        this.description = description; }
}