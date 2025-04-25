package com.lucas.tasktracker.controllers;

import com.lucas.tasktracker.dtos.requests.AddMemberDTO;
import com.lucas.tasktracker.dtos.requests.AddTaskListDTO;
import com.lucas.tasktracker.dtos.requests.RequestProjectDTO;
import com.lucas.tasktracker.dtos.responses.ResponseProjectDTO;
import com.lucas.tasktracker.dtos.responses.ResponseTaskListDTO;
import com.lucas.tasktracker.dtos.responses.ResponseUserDTO;
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

    // Obtener detalles de un proyecto.
    @GetMapping("/{projectId}")
    public ResponseEntity<ResponseProjectDTO> getProjectByID(@PathVariable Long projectId) {
        ResponseProjectDTO responseProjectDTO = projectService.getProjectById(projectId).orElse(null);

        return ResponseEntity.ok(responseProjectDTO);
    }

    // Crear un nuevo proyecto.
    @PostMapping
    public ResponseEntity<ResponseProjectDTO> createProject(@RequestBody RequestProjectDTO requestProjectDTO) {
        ResponseProjectDTO newProjectDTO = projectService.createProject(requestProjectDTO);

        return ResponseEntity.ok(newProjectDTO);
    }

    // Obtener lista paginada/ordenada de proyectos.
    @GetMapping
    public ResponseEntity<List<ResponseProjectDTO>> getAllProjects() {
        List<ResponseProjectDTO> responseProjectDTOS = projectService.getAllProjects();

        return ResponseEntity.ok(responseProjectDTOS);
    }

    // Eliminar un proyecto.
    @DeleteMapping("/{project_id}")
    public ResponseEntity<List<ResponseProjectDTO>> deleteProject(@PathVariable Long project_id) {
        List<ResponseProjectDTO> responseProjectDTOList  = projectService.deleteProject(project_id).orElse(null);

        return ResponseEntity.ok(responseProjectDTOList);
    }

    // Actualizar un proyecto.
    @PatchMapping("/{projectId}")
    public ResponseEntity<ResponseProjectDTO> updateProject(@PathVariable Long projectId, @RequestBody RequestProjectDTO requestProjectDTO) {
        ResponseProjectDTO responseProjectDTOList = projectService.updateProject(projectId, requestProjectDTO).orElse(null);

        return ResponseEntity.ok(responseProjectDTOList);
    }

    // Listar los usuarios miembros de un proyecto.
    @GetMapping("/{project_id}/members")
    public ResponseEntity<Set<ResponseUserDTO>> getProjectMembers(@PathVariable Long project_id) {
        Set<ResponseUserDTO> projectMembersDTO = projectService.getProjectMembers(project_id).orElse(null);

        return ResponseEntity.ok(projectMembersDTO);
    }

    // Añadir un usuario como miembro a un proyecto.
    @PostMapping("/{project_id}/members")
    public ResponseEntity<Set<ResponseUserDTO>> addUserToProject(@PathVariable Long project_id, @RequestBody AddMemberDTO addMemberDTO) {
        Set<ResponseUserDTO> projectMembersDTO = projectService.addMemberToProject(project_id, addMemberDTO).orElse(null);

        return ResponseEntity.ok(projectMembersDTO);
    }

    //    DELETE /api/projects/{projectId}/members/{userId}: Quitar un usuario de un proyecto.
    @DeleteMapping("{projectId}/members/{memberId}")
    public ResponseEntity<Set<ResponseUserDTO>> removeUserFromProject(@PathVariable Long projectId, @PathVariable Long memberId) {
        Set<ResponseUserDTO> projectMembersDTO = projectService.deleteProjectMember(projectId, memberId).orElse(null);

        return ResponseEntity.ok(projectMembersDTO);
    }


    // Agregar una nueva lista de tareas dentro de un proyecto.
    @PostMapping("/{projectId}/tasklists")
    public ResponseEntity<ResponseProjectDTO> addTaskListToProject(@PathVariable Long projectId, @RequestBody AddTaskListDTO addTaskListDTO) {
        ResponseProjectDTO responseProjectDTO = projectService.addTaskListToProject(projectId, addTaskListDTO).orElse(null);

        return ResponseEntity.ok(responseProjectDTO);
    }

    // Listar las listas de tareas de un proyecto.
    @GetMapping("/{projectId}/tasklists")
    public ResponseEntity<Set<ResponseTaskListDTO>> getProjectTaskLists(@PathVariable Long projectId) {
        Set<ResponseTaskListDTO> projectResponseTaskListDTO = projectService.getProjectTaskLists(projectId).orElse(null);

        return ResponseEntity.ok(projectResponseTaskListDTO);
    }


}
