# repoIntegrador



Team Somerville (Recuperación y Gestión de Estado)
Required use cases:
CreateProject (Implementado para que las pruebas funcionen)

CreateTask (Implementado para que las pruebas funcionen, con reglas de Proyecto Cerrado y Horas Positivas)

FindProjectById

FindAllTasksByProjectId

UpdateProjectStatus (con lógica de No Cerrar con Tareas Activas)

UpdateTaskStatus (con lógica de finishedAt)

Endpoints to implement:
POST /projects

POST /projects/{projectId}/tasks

GET /projects/{projectId}

GET /projects/{projectId}/tasks

PATCH /projects/{projectId}/status

PATCH /projects/{projectId}/tasks/{taskId}/status
