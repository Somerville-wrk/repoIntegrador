package com.pa1.integrador.core.model.port.output;
import com.pa1.integrador.core.model.Project;
import java.util.Optional;

public interface IPuertorRepoProyecto {

    Optional<Project> findById(Long id);

    Project save(Project project);
}
