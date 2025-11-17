package com.pa1.integrador.core.model.port.input;
import com.pa1.integrador.core.model.Task;
import java.util.List;

public interface IEcontrarTodasLasTareasPorIdProyecto {
    List<Task> findAllByProjectId(Long projectId);
}
