package com.pa1.integrador.core.model.port.input;
import com.pa1.integrador.core.model.Project;
import com.pa1.integrador.core.model.ProjectStatus;
import java.time.LocalDate;

public interface ICrearProyecto {
    Project createProject(String name, LocalDate startDate, LocalDate endDate, ProjectStatus status, String description);
}
