package com.pa1.integrador.adapter.data;

import com.pa1.integrador.adapter.data.jpa.entity.ProjectEntity;
import com.pa1.integrador.adapter.data.jpa.repository.IProjectJpaRepository;
import com.pa1.integrador.adapter.data.mapper.PersistenceMapper;
import com.pa1.integrador.core.model.Project;
import com.pa1.integrador.core.model.port.output.IPuertoRepoProyecto;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component // Lo marcamos como Componente. ¡Esto resuelve el error de "No beans found"!
public class ProjectRepositoryAdapter implements IPuertoRepoProyecto {

    private final IProjectJpaRepository jpaRepository;
    private final PersistenceMapper mapper;

    public ProjectRepositoryAdapter(IProjectJpaRepository jpaRepository, PersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Project> findById(Long id) {
        Optional<ProjectEntity> entityOptional = jpaRepository.findById(id);

        return entityOptional.map(mapper::projectEntityToProject);
    }

    @Override
    public Project save(Project project) {
        ProjectEntity entity = mapper.projectToProjectEntity(project);

        ProjectEntity savedEntity = jpaRepository.save(entity);

        return mapper.projectEntityToProject(savedEntity);
    }
}