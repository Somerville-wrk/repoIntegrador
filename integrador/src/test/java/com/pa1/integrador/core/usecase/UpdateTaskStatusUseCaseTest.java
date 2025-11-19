package com.pa1.integrador.core.usecase;

import com.pa1.integrador.core.exception.RecursoNoEncontradoExcepcion;
import com.pa1.integrador.core.model.Project;
import com.pa1.integrador.core.model.ProjectStatus;
import com.pa1.integrador.core.model.Task;
import com.pa1.integrador.core.model.TaskStatus;
import com.pa1.integrador.core.model.port.output.IPuertoRepoTareas;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings; // <--- NUEVO IMPORT
import org.mockito.quality.Strictness;         // <--- NUEVO IMPORT

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// AÑADIR LA CONFIGURACIÓN LENIENT AQUÍ
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT) // <--- CORRECCIÓN CLAVE
public class UpdateTaskStatusUseCaseTest {

    @Mock
    private IPuertoRepoTareas taskRepository;

    @Mock
    private Clock fixedClock;

    @InjectMocks
    private UpdateTaskStatusUseCase useCase;

    private final Long TASK_ID = 1L;
    private Task existingTask;
    private Project parentProject;
    private final LocalDateTime FAKE_NOW = LocalDateTime.of(2025, 1, 1, 10, 0);
    private final LocalDateTime TASK_CREATED_AT = LocalDateTime.of(2024, 1, 1, 1, 1);

    @BeforeEach
    void setUp() {
        // 1. Configuración del Clock
        when(fixedClock.instant()).thenReturn(FAKE_NOW.atZone(ZoneId.systemDefault()).toInstant());
        when(fixedClock.getZone()).thenReturn(ZoneId.systemDefault());

        // 2. Creación de Objetos Base
        parentProject = Project.of(
                10L,
                "Nombre Proyecto",
                LocalDate.now(),
                LocalDate.now(),
                ProjectStatus.ACTIVE,
                "Desc");

        existingTask = Task.create(
                parentProject,
                "Tarea de prueba",
                10,
                "Asignado",
                TaskStatus.TODO,
                TASK_CREATED_AT);
        existingTask.setId(TASK_ID);
    }

    // --- Caso 1: Regla de Negocio (finishedAt) ---
    @Test
    void whenStatusChangesToDone_shouldSetFinishedAtTime() {
        when(taskRepository.findById(TASK_ID)).thenReturn(Optional.of(existingTask));

        useCase.updateStatus(TASK_ID, TaskStatus.DONE);

        assertEquals(TaskStatus.DONE, existingTask.getStatus());
        assertEquals(FAKE_NOW, existingTask.getFinishedAt());

        verify(taskRepository, times(1)).save(existingTask);
    }

    // --- Caso 2: Error (404) ---
    @Test
    void whenTaskNotFound_shouldThrowResourceNotFoundException() {
        when(taskRepository.findById(TASK_ID)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoExcepcion.class, () -> {
            useCase.updateStatus(TASK_ID, TaskStatus.DONE);
        });

        verify(taskRepository, never()).save(any(Task.class));
    }
}