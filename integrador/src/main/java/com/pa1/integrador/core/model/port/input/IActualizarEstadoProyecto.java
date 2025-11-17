package com.pa1.integrador.core.model.port.input;

import com.pa1.integrador.core.model.Project;
import com.pa1.integrador.core.model.ProjectStatus;

public interface IActualizarEstadoProyecto {
    Project updateStatus(Long projectId, ProjectStatus newStatus);
}
