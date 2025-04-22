package com.lucas.tasktracker.controllers;

import com.lucas.tasktracker.dtos.AddMemberDTO;
import com.lucas.tasktracker.dtos.ProjectDTO;
import com.lucas.tasktracker.dtos.UpdateProjectDTO;
import com.lucas.tasktracker.dtos.UserDTO;
import com.lucas.tasktracker.repositories.ProjectRepository;
import com.lucas.tasktracker.services.ProjectService;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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

    // Obtener detalles de un proyecto.
    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectDTO> getProjectByID(@PathVariable Long projectId) {
        ProjectDTO projectDTO = projectService.getProjectById(projectId).orElse(null);

        return ResponseEntity.ok(projectDTO);
    }

    // Crear un nuevo proyecto.
    @PostMapping
    public ResponseEntity<ProjectDTO> createProject(@RequestBody ProjectDTO projectDTO) {
        ProjectDTO newProjectDTO = projectService.createProject(projectDTO);

        return ResponseEntity.ok(newProjectDTO);
    }

    // Obtener lista paginada/ordenada de proyectos.
    @GetMapping
    public ResponseEntity<List<ProjectDTO>> getAllProjects() {
        List<ProjectDTO> projectsDTO = projectService.getAllProjects();

        return ResponseEntity.ok(projectsDTO);
    }

    // Eliminar un proyecto.
    @DeleteMapping("/{project_id}")
    public ResponseEntity<List<ProjectDTO>> deleteProject(@PathVariable Long project_id) {
        List<ProjectDTO> projectsDTO = projectService.deleteProject(project_id);

        return ResponseEntity.ok(projectsDTO);
    }

    // Actualizar un proyecto.
    @PatchMapping("/{projectId}")
    public ResponseEntity<ProjectDTO> updateProject(@PathVariable Long projectId, @RequestBody UpdateProjectDTO updateProjectDTO) {
        ProjectDTO projectDTO = projectService.updateProject(projectId, updateProjectDTO).orElse(null);

        return ResponseEntity.ok(projectDTO);
    }

    // Listar los usuarios miembros de un proyecto.
    @GetMapping("/{project_id}/members")
    public ResponseEntity<Set<UserDTO>> getProjectMembers(@PathVariable Long project_id) {
        Set<UserDTO> projectMembersDTO = projectService.getProjectMembers(project_id).orElse(null);

        return ResponseEntity.ok(projectMembersDTO);
    }

    // Añadir un usuario como miembro a un proyecto.
    @PostMapping("/{project_id}/members")
    public ResponseEntity<Set<UserDTO>> addUserToProject(@PathVariable Long project_id, @RequestBody AddMemberDTO addMemberDTO) {
        Set<UserDTO> projectMembersDTO = projectService.addMemberToProject(project_id, addMemberDTO).orElse(null);

        return ResponseEntity.ok(projectMembersDTO);
    }

    //    DELETE /api/projects/{projectId}/members/{userId}: Quitar un usuario de un proyecto.
    @DeleteMapping("{projectId}/members/{memberId}")
    public ResponseEntity<Set<UserDTO>> removeUserFromProject(@PathVariable Long projectId, @PathVariable Long memberId) {
        Set<UserDTO> projectMembersDTO = projectService.deleteProjectMember(projectId, memberId).orElse(null);

        return ResponseEntity.ok(projectMembersDTO);
    }


    //    POST /api/projects/{projectId}/tasklists: Crear una nueva lista de tareas dentro de un proyecto.
    //    GET /api/projects/{projectId}/tasklists: Listar las listas de tareas de un proyecto.
}
