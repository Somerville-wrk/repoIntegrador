package com.pa1.integrador.core.usecase;

import com.pa1.integrador.core.exception.ViolacionReglaDeNegocioExcepcion;
import com.pa1.integrador.core.model.Project;
import com.pa1.integrador.core.model.ProjectStatus;
import com.pa1.integrador.core.model.port.input.ICrearProyecto;
import com.pa1.integrador.core.model.port.output.IPuertoRepoProyecto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CreateProjectUseCase implements ICrearProyecto {

    private final IPuertoRepoProyecto projectRepository;

    public CreateProjectUseCase(IPuertoRepoProyecto projectRepository) {

        this.projectRepository = projectRepository;
    }

    @Override
    public Project createProject(String name, LocalDate startDate, LocalDate endDate, ProjectStatus status, String description) {


        if (endDate.isBefore(startDate)) {

            throw new ViolacionReglaDeNegocioExcepcion("La fecha de finalización no puede ser anterior a la fecha de inicio.");
        }

        Project newProject = Project.create(name, startDate, endDate, status, description);

        return projectRepository.save(newProject);
    }
}