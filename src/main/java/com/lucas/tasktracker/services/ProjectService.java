package com.lucas.tasktracker.services;

import com.lucas.tasktracker.dtos.requests.AddMemberDTO;
import com.lucas.tasktracker.dtos.requests.AddTaskListDTO;
import com.lucas.tasktracker.dtos.requests.RequestProjectDTO;
import com.lucas.tasktracker.dtos.responses.ResponseProjectDTO;
import com.lucas.tasktracker.dtos.responses.ResponseTaskListDTO;
import com.lucas.tasktracker.dtos.responses.ResponseUserDTO;
import com.lucas.tasktracker.entities.ProjectEntity;
import com.lucas.tasktracker.entities.TaskListEntity;
import com.lucas.tasktracker.entities.UserEntity;
import com.lucas.tasktracker.mappers.ProjectMapper;
import com.lucas.tasktracker.mappers.TaskListMapper;
import com.lucas.tasktracker.mappers.UserMapper;
import com.lucas.tasktracker.repositories.ProjectRepository;
import com.lucas.tasktracker.repositories.TaskListRepository;
import com.lucas.tasktracker.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    private final TaskListRepository taskListRepository;
    ProjectMapper projectMapper;
    ProjectRepository projectRepository;
    UserMapper userMapper;
    UserRepository userRepository;
    TaskListMapper taskListMapper;

    public ProjectService(ProjectMapper projectMapper, ProjectRepository projectRepository, UserMapper userMapper, UserRepository userRepository, TaskListRepository taskListRepository, TaskListMapper taskListMapper) {
        this.projectMapper = projectMapper;
        this.projectRepository = projectRepository;
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.taskListRepository = taskListRepository;
        this.taskListMapper = taskListMapper;
    }

    // Crear un nuevo proyecto.
    @Transactional
    public ResponseProjectDTO createProject(RequestProjectDTO requestProjectDTO) {
        ProjectEntity projectEntity = projectMapper.toProjectEntity(requestProjectDTO);
        projectRepository.save(projectEntity);
        ResponseProjectDTO savedProjectDTO = projectMapper.toResponseProjectDTO(projectEntity);

        return savedProjectDTO;
    }

    // Eliminar un proyecto.
    @Transactional
    public Optional<List<ResponseProjectDTO>> deleteProject(Long projectId) {
        Optional<ProjectEntity> projectEntityOptional = projectRepository.findById(projectId);

        if(projectEntityOptional.isEmpty()) {
            return Optional.empty();
        }


        // Remover al proyecto de todas las listas de usuarios que lo tengan en su entidad

        // 1. Recorrer el listado de members que tiene este proyecto
        Set<UserEntity> projectMembers = projectEntityOptional.get().getMembers();

        for(UserEntity member : projectMembers) {
            member.getProjects().removeIf(project -> project.getId().equals(projectId));
        }

        projectRepository.deleteById(projectId);

        List<ProjectEntity> projectEntityList = projectRepository.findAll();
        List<ResponseProjectDTO> projectsDTOList = projectEntityList.stream()
                .map(projectMapper::toResponseProjectDTO)
                .toList();

        return Optional.of(projectsDTOList);
    }

    // Añadir un usuario como miembro a un proyecto.
    @Transactional
    public Optional<Set<ResponseUserDTO>> addMemberToProject(Long projectId, AddMemberDTO addMemberDTO) {
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
                Set<ResponseUserDTO> projectMembersDTOList = projectMembersEntityList.stream()
                        .map(userMapper::toResponseUserDTO)
                        .collect(Collectors.toSet());

                return Optional.of(projectMembersDTOList);
            } else {
                return Optional.empty();
            }
        }

        return Optional.empty();
    }



    // Obtener lista paginada/ordenada de proyectos.
    public List<ResponseProjectDTO> getAllProjects() {
        List<ProjectEntity> projectsEntity = projectRepository.findAll();
        List<ResponseProjectDTO> projectsDTO = projectsEntity.stream()
                .map(projectMapper::toResponseProjectDTO)
                .collect(Collectors.toList());

        return projectsDTO;
    }


    // Obtener detalles de un proyecto.
    public Optional<ResponseProjectDTO> getProjectById(Long projectId) {
        Optional<ProjectEntity> projectEntityOptional = projectRepository.findById(projectId);

        if(projectEntityOptional.isPresent()) {
            ProjectEntity projectEntity = projectEntityOptional.get();
            ResponseProjectDTO projectDTO = projectMapper.toResponseProjectDTO(projectEntity);

            return Optional.of(projectDTO);
        }

        return Optional.empty();
    }


    // Actualizar un proyecto.
    public Optional<ResponseProjectDTO> updateProject(Long projectId, RequestProjectDTO requestProjectDTO) {
        Optional<ProjectEntity> projectEntityOptional = projectRepository.findById(projectId);

        if(projectEntityOptional.isPresent()) {
            ProjectEntity projectEntity = projectEntityOptional.get();

            if(requestProjectDTO.getTitle() != null) {
                projectEntity.setTitle(requestProjectDTO.getTitle());
                projectRepository.save(projectEntity);

                ResponseProjectDTO updatedProjectDTO = projectMapper.toResponseProjectDTO(projectEntity);

                return Optional.of(updatedProjectDTO);
            }
        }
        return Optional.empty();
    }

    // Quitar un usuario de un proyecto.
    @Transactional
    public Optional<Set<ResponseUserDTO>> deleteProjectMember(Long projectId, Long memberId) {
        Optional<ProjectEntity> projectEntityOptional = projectRepository.findById(projectId);
        Optional<UserEntity> memberEntityOptional = userRepository.findById(memberId);

        if (projectEntityOptional.isEmpty() || memberEntityOptional.isEmpty()) {
            return Optional.empty();
        }

        ProjectEntity projectEntity = projectEntityOptional.get();
        Set<UserEntity> currentMembers = projectEntity.getMembers();

        UserEntity memberToRemove = memberEntityOptional.get();

        Set<ProjectEntity> memberProjectList =  memberEntityOptional.get().getProjects();
        currentMembers.remove(memberToRemove);
        memberProjectList.remove(projectEntity);

        projectRepository.save(projectEntity);

        Set<ResponseUserDTO> updatedMembersDTO = projectEntity.getMembers().stream()
                .map(userMapper::toResponseUserDTO)
                .collect(Collectors.toSet());

        return Optional.of(updatedMembersDTO);
    }

    //  Listar los usuarios miembros de un proyecto.
    public Optional<Set<ResponseUserDTO>> getProjectMembers(Long projectId) {
        Optional<ProjectEntity> projectEntityOptional = projectRepository.findById(projectId);

        if(projectEntityOptional.isEmpty()) {
            return Optional.empty();
        }

        Set<UserEntity> projectMembersEntityList = projectEntityOptional.get().getMembers();
        Set<ResponseUserDTO> projectMembersDTOList = projectMembersEntityList.stream()
                .map(userMapper::toResponseUserDTO)
                .collect(Collectors.toSet());

        return Optional.of(projectMembersDTOList);
    }

    // Agregar una nueva lista de tareas dentro de un proyecto.
    public Optional<ResponseProjectDTO> addTaskListToProject(Long projectId, AddTaskListDTO addTaskListId) {
        Optional<ProjectEntity> projectEntityOptional = projectRepository.findById(projectId);
        Optional<TaskListEntity> taskListEntityOptional = taskListRepository.findById(addTaskListId.getTaskListId());

        if(projectEntityOptional.isEmpty() || taskListEntityOptional.isEmpty()) {
            return Optional.empty();
        }

        ProjectEntity projectEntity = projectEntityOptional.get();
        TaskListEntity taskListEntity = taskListEntityOptional.get();

        boolean isACurrentTaskList = projectEntity.getTaskLists().contains(taskListEntity);

        if(!isACurrentTaskList) {
            taskListEntity.setProject(projectEntity);
            projectEntity.getTaskLists().add(taskListEntity);

            projectRepository.save(projectEntity);

            ResponseProjectDTO responseProjectDTO = projectMapper.toResponseProjectDTO(projectEntity);
            return Optional.of(responseProjectDTO);
        }

        return Optional.empty();
    }


    // Listar las listas de tareas de un proyecto.

    public Optional<Set<ResponseTaskListDTO>> getProjectTaskLists(Long projectId) {

        Optional<ProjectEntity> projectEntityOptional = projectRepository.findById(projectId);

        if(projectEntityOptional.isEmpty()) {
            Optional.empty();
        }

        ProjectEntity projectEntity = projectEntityOptional.get();
        Set<TaskListEntity> projectTaskListEntity = projectEntity.getTaskLists();

        Set<ResponseTaskListDTO> projectResponseTaskListDTO = projectTaskListEntity.stream()
                .map(taskListMapper::toResponseTaskListDTO)
                .collect(Collectors.toSet());

        return Optional.of(projectResponseTaskListDTO);
    }

}
