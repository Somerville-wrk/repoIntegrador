package com.pa1.integrador.core.model.port.input;

import com.pa1.integrador.core.exception.RecursoNoEncontradoExcepcion;
import com.pa1.integrador.core.exception.ViolacionReglaDeNegocioExcepcion;
import com.pa1.integrador.core.model.Task;
import com.pa1.integrador.core.model.TaskStatus;

public interface ICrearTarea {
    Task createTask(Long projectId, String title, Integer estimateHours, String assignee, TaskStatus status)
            throws RecursoNoEncontradoExcepcion, ViolacionReglaDeNegocioExcepcion;
}
