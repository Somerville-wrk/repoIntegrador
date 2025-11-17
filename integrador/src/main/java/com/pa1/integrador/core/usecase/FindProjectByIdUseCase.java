package com.pa1.integrador.core.usecase;

import com.pa1.integrador.core.exception.RecursoNoEncontradoExcepcion;
import com.pa1.integrador.core.model.Project;
import com.pa1.integrador.core.model.port.input.IEncontrarProyectoPorID;
import com.pa1.integrador.core.model.port.output.IPuertoRepoProyecto;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FindProjectByIdUseCase implements IEncontrarProyectoPorID {

    private final IPuertoRepoProyecto projectRepository;

    public FindProjectByIdUseCase(IPuertoRepoProyecto projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public Optional<Project> findById(Long id) {
        // La lógica del controlador (adapter) manejará el Optional
        return projectRepository.findById(id);
    }
}