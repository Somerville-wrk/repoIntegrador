package com.pa1.integrador.adapter.data.jpa.repository;

import com.pa1.integrador.adapter.data.jpa.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITaskJpaRepository extends JpaRepository<TaskEntity, Long> {

    List<TaskEntity> findAllByProjectId(Long projectId);
}