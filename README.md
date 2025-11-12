# repoIntegrador



Team Somerville (Recuperación y Gestión de Estado)
Required use cases:
FindProjectById: Obtener un solo proyecto por su ID.

FindAllTasksByProjectId: Obtener todas las tareas asociadas a un proyecto específico.

UpdateTaskStatus: Caso de uso específico para cambiar solo el estado de una tarea

Regla de negocio: Al cambiar el estado a DONE, este caso de uso debe establecer el campo finishedAt = now()

UpdateProjectStatus: Caso de uso específico para cambiar solo el estado de un proyecto 

Regla de negocio: Implementa las validaciones necesarias para el cambio de estado (ej. un proyecto CLOSED no puede volver a ACTIVE, o un proyecto no puede pasar a CLOSED si tiene tareas IN_PROGRESS).

Endpoints to implement:
GET /projects/{projectId}

GET /projects/{projectId}/tasks

PATCH /projects/{projectId}/tasks/{taskId}/status

PATCH /projects/{projectId}/status 
