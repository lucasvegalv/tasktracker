package com.lucas.tasktracker.services;

import com.lucas.tasktracker.dtos.AddMemberDTO;
import com.lucas.tasktracker.dtos.ProjectDTO;
import com.lucas.tasktracker.dtos.UserDTO;
import com.lucas.tasktracker.entities.ProjectEntity;
import com.lucas.tasktracker.entities.UserEntity;
import com.lucas.tasktracker.mappers.ProjectMapper;
import com.lucas.tasktracker.mappers.UserMapper;
import com.lucas.tasktracker.repositories.ProjectRepository;
import com.lucas.tasktracker.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    ProjectMapper projectMapper;
    ProjectRepository projectRepository;
    UserMapper userMapper;
    UserRepository userRepository;

    public ProjectService(ProjectMapper projectMapper, ProjectRepository projectRepository, UserMapper userMapper, UserRepository userRepository) {
        this.projectMapper = projectMapper;
        this.projectRepository = projectRepository;
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    // Crear un nuevo proyecto.
    @Transactional
    public ProjectDTO createProject(ProjectDTO projectDTO) {
        ProjectEntity projectEntity = projectMapper.toProjectEntity(projectDTO);
        projectRepository.save(projectEntity);
        ProjectDTO savedProjectDTO = projectMapper.toProjectDTO(projectEntity);

        return savedProjectDTO;
    }

    // Eliminar un proyecto.
    public List<ProjectDTO> deleteProject(Long projectId) {
        Optional<ProjectEntity> projectEntityOptional = projectRepository.findById(projectId);
        List<ProjectDTO> projectsDTOList;

        if(projectEntityOptional.isPresent()) {
            projectRepository.deleteById(projectId);
        }

        List<ProjectEntity> projectEntityList = projectRepository.findAll();
        projectsDTOList = projectEntityList.stream()
                .map(projectMapper::toProjectDTO)
                .collect(Collectors.toList());

        return projectsDTOList;
    }

    //  Listar los usuarios miembros de un proyecto.
    public Optional<Set<UserDTO>> getProjectMembers(Long projectId) {
        Optional<ProjectEntity> projectEntityOptional = projectRepository.findById(projectId);
        Set<UserEntity> projectMembersEntityList;
        Set<UserDTO> projectMembersDTOList;

        if(projectEntityOptional.isPresent()) {
            projectMembersEntityList = projectEntityOptional.get().getMembers();
            projectMembersDTOList = projectMembersEntityList.stream()
                    .map(userMapper::toUserDTO)
                    .collect(Collectors.toSet());

            return Optional.of(projectMembersDTOList);
        }

        return Optional.empty();
    }

    // Añadir un usuario como miembro a un proyecto.
    @Transactional
    public Optional<Set<UserDTO>> addMemberToProject(Long projectId, AddMemberDTO addMemberDTO) {
        Optional<ProjectEntity> projectEntityOptional = projectRepository.findById(projectId);
        Optional<UserEntity> userEntityOptional = userRepository.findById(addMemberDTO.getMemberId());

        if(userEntityOptional.isPresent() && projectEntityOptional.isPresent()) {
            ProjectEntity projectEntity = projectEntityOptional.get();
            UserEntity userEntity = userEntityOptional.get();

            boolean alreadyMember = projectEntity.getMembers().contains(userEntity);
            if(!alreadyMember) {
                projectEntity.getMembers().add(userEntity);
                userEntity.getProjects().add(projectEntity);

                userRepository.save(userEntity);

                Set<UserEntity> projectMembersEntityList = projectEntity.getMembers();
                Set<UserDTO> projectMembersDTOList = projectMembersEntityList.stream()
                        .map(userMapper::toUserDTO)
                        .collect(Collectors.toSet());

                return Optional.of(projectMembersDTOList);
            } else {
                return Optional.empty();
            }
        }

        return Optional.empty();
    }



    //    GET /api/projects: Obtener lista paginada/ordenada de proyectos.
    public List<ProjectDTO> getAllProjects() {
        List<ProjectEntity> projectsEntity = projectRepository.findAll();
        List<ProjectDTO> projectsDTO = projectsEntity.stream()
                .map(projectMapper::toProjectDTO)
                .collect(Collectors.toList());

        return projectsDTO;
    }



    //    GET /api/projects/{projectId}: Obtener detalles de un proyecto.
    //    PUT /api/projects/{projectId}: Actualizar un proyecto.
    //    DELETE /api/projects/{projectId}/members/{userId}: Quitar un usuario de un proyecto.
    //    POST /api/projects/{projectId}/tasklists: Crear una nueva lista de tareas dentro de un proyecto.
    //    GET /api/projects/{projectId}/tasklists: Listar las listas de tareas de un proyecto.
}
