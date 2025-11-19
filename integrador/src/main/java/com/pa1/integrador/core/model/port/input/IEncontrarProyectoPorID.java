package com.pa1.integrador.core.model.port.input;

import com.pa1.integrador.core.model.Project;
import java.util.Optional;

public interface IEncontrarProyectoPorID {
    Optional<Project> findById(Long id);
}
