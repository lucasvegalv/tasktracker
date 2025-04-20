package com.lucas.tasktracker.controllers;

import com.lucas.tasktracker.dtos.AddMemberDTO;
import com.lucas.tasktracker.dtos.ProjectDTO;
import com.lucas.tasktracker.dtos.UserDTO;
import com.lucas.tasktracker.repositories.ProjectRepository;
import com.lucas.tasktracker.services.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectRepository projectRepository;
    ProjectService projectService;

    public ProjectController(ProjectService projectService, ProjectRepository projectRepository) {
        this.projectService = projectService;
        this.projectRepository = projectRepository;
    }

    // Crear un nuevo proyecto.
    @PostMapping
    public ResponseEntity<ProjectDTO> createProject(@RequestBody ProjectDTO projectDTO) {
        ProjectDTO newProjectDTO = projectService.createProject(projectDTO);

        return ResponseEntity.ok(newProjectDTO);
    }

    // Eliminar un proyecto.
    @DeleteMapping("/{project_id}")
    public ResponseEntity<List<ProjectDTO>> deleteProject(@PathVariable Long project_id) {
        List<ProjectDTO> projectsDTO = projectService.deleteProject(project_id);

        return ResponseEntity.ok(projectsDTO);
    }

    //    GET /api/projects/{projectId}/members: Listar los usuarios miembros de un proyecto.
    @GetMapping("/{project_id}")
    public ResponseEntity<Set<UserDTO>> getProjectMembers(@PathVariable Long project_id) {
        Set<UserDTO> projectMembersDTO = projectService.getProjectMembers(project_id).orElse(null);

        return ResponseEntity.ok(projectMembersDTO);
    }

    //    POST /api/projects/{projectId}/members: Añadir un usuario como miembro a un proyecto.
    @PostMapping("/{project_id}/members")
    public ResponseEntity<Set<UserDTO>> addUserToProject(@PathVariable Long project_id, @RequestBody AddMemberDTO addMemberDTO) {
        Set<UserDTO> projectMembersDTO = projectService.addMemberToProject(project_id, addMemberDTO).orElse(null);

        return ResponseEntity.ok(projectMembersDTO);
    }


    //    GET /api/projects: Obtener lista paginada/ordenada de proyectos.
    @GetMapping
    public ResponseEntity<List<ProjectDTO>> getAllProjects() {
        List<ProjectDTO> projectsDTO = projectService.getAllProjects();

        return ResponseEntity.ok(projectsDTO);
    }

    //    GET /api/projects/{projectId}: Obtener detalles de un proyecto.
    //    PUT /api/projects/{projectId}: Actualizar un proyecto.
    //    DELETE /api/projects/{projectId}/members/{userId}: Quitar un usuario de un proyecto.
    //    POST /api/projects/{projectId}/tasklists: Crear una nueva lista de tareas dentro de un proyecto.
    //    GET /api/projects/{projectId}/tasklists: Listar las listas de tareas de un proyecto.
}
