package com.pa1.integrador.adapter.data.jpa.repository;

import com.pa1.integrador.adapter.data.jpa.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProjectJpaRepository extends JpaRepository<ProjectEntity, Long> {

}