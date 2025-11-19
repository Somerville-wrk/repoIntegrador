package com.pa1.integrador.core.model.port.input;

import com.pa1.integrador.core.model.Task;
import com.pa1.integrador.core.model.TaskStatus;
public interface IActualizarEstadoTarea {
    Task updateStatus(Long taskId, TaskStatus newStatus);
}
