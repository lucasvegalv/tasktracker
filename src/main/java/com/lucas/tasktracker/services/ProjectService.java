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
import com.lucas.tasktracker.exceptions.BadArgumentExceptionType;
import com.lucas.tasktracker.exceptions.NotFoundExceptionType;
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

        Optional<ProjectEntity> projectEntityOptional = projectRepository.findByTitle(requestProjectDTO.getTitle());
        if (projectEntityOptional.isPresent()) {
            throw BadArgumentExceptionType.PROJECT_ALREADY_EXISTS.getException();
        }


        ProjectEntity projectEntity = projectMapper.toProjectEntity(requestProjectDTO);
        projectRepository.save(projectEntity);
        ResponseProjectDTO savedProjectDTO = projectMapper.toResponseProjectDTO(projectEntity);

        return savedProjectDTO;
    }

    // Eliminar un proyecto.
    @Transactional
    public List<ResponseProjectDTO> deleteProject(Long projectId) {

        // Validamos de que exista y asi poder eliminarlo. Si no existe lanzamos un PROJECT_NOT_FOUND
        Optional<ProjectEntity> projectEntityOptional = projectRepository.findById(projectId);

        if(projectEntityOptional.isEmpty()) {
            throw NotFoundExceptionType.PROJECT_NOT_FOUND.getException();
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

        return projectsDTOList;
    }

    // Añadir un usuario como miembro a un proyecto.
    @Transactional
    public Set<ResponseUserDTO> addMemberToProject(Long projectId, AddMemberDTO addMemberDTO) {

        Optional<ProjectEntity> projectEntityOptional = projectRepository.findById(projectId);
        Optional<UserEntity> userEntityOptional = userRepository.findById(addMemberDTO.getMemberId());
        // Validar que el proyecto exista. Si no existe lanzamos un PROJECT_NOT_FOUND
        if(projectEntityOptional.isEmpty()) {
            throw NotFoundExceptionType.PROJECT_NOT_FOUND.getException();
        }

        // Validar que el usuario exista. Si no existe lanzamos un USER_NOT_FOUND
        if(userEntityOptional.isEmpty()) {
            throw NotFoundExceptionType.USER_NOT_FOUND.getException();
        }


        // Validar de que el usuario no sea parte del proyecto. Si lo es, lanzamos un USER_ALREADY_MEMBER
        ProjectEntity projectEntity = projectEntityOptional.get();
        UserEntity userEntity = userEntityOptional.get();

        boolean alreadyMember = projectEntity.getMembers().contains(userEntity);
        if(!alreadyMember) {
            throw BadArgumentExceptionType.USER_ALREADY_MEMBER.getException();
        }

        projectEntity.getMembers().add(userEntity);
        userEntity.getProjects().add(projectEntity);

        userRepository.save(userEntity);

        Set<UserEntity> projectMembersEntityList = projectEntity.getMembers();
        Set<ResponseUserDTO> projectMembersDTOList = projectMembersEntityList.stream()
                .map(userMapper::toResponseUserDTO)
                .collect(Collectors.toSet());

        return projectMembersDTOList;
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

        // Validar que el proyecto exista. Si no existe lanzamos un PROJECT_NOT_FOUND
        Optional<ProjectEntity> projectEntityOptional = projectRepository.findById(projectId);

        if(projectEntityOptional.isEmpty()) {
            throw NotFoundExceptionType.PROJECT_NOT_FOUND.getException();
        }

        ProjectEntity projectEntity = projectEntityOptional.get();
        ResponseProjectDTO projectDTO = projectMapper.toResponseProjectDTO(projectEntity);

        return Optional.empty();
    }


    // Actualizar un proyecto.
    public ResponseProjectDTO updateProject(Long projectId, RequestProjectDTO requestProjectDTO) {

        // Validar que el proyecto exista. Si no existe lanzamos un PROJECT_NOT_FOUND

        // Validar de que el nombre del proyecto no este en uso ya. Si lo esta, lanzamos un PROJECT_ALREADY_EXISTS

        Optional<ProjectEntity> projectEntityOptional = projectRepository.findById(projectId);

        if(projectEntityOptional.isEmpty()) {
            throw NotFoundExceptionType.PROJECT_NOT_FOUND.getException();
        }

        ProjectEntity projectEntity = projectEntityOptional.get();

        Optional<ProjectEntity> projectEntityOptional2 = projectRepository.findByTitle(projectEntity.getTitle());

        if(projectEntityOptional2.isPresent()) {
            throw BadArgumentExceptionType.PROJECT_ALREADY_EXISTS.getException();
        }

        projectEntity.setTitle(requestProjectDTO.getTitle());
        projectRepository.save(projectEntity);

        ResponseProjectDTO updatedProjectDTO = projectMapper.toResponseProjectDTO(projectEntity);

        return updatedProjectDTO;

    }

    // Quitar un usuario de un proyecto.
    @Transactional
    public Set<ResponseUserDTO> deleteProjectMember(Long projectId, Long memberId) {
        Optional<ProjectEntity> projectEntityOptional = projectRepository.findById(projectId);
        Optional<UserEntity> memberEntityOptional = userRepository.findById(memberId);

        // Validar que el proyecto exista. Si no existe lanzamos un PROJECT_NOT_FOUND
        if(projectEntityOptional.isEmpty()) {
            throw NotFoundExceptionType.PROJECT_NOT_FOUND.getException();
        }

        // Validar que el usuario existao. Si no existe lanzamos un USER_NOT_FOUND
        if(memberEntityOptional.isEmpty()) {
            throw NotFoundExceptionType.USER_NOT_FOUND.getException();
        }

        ProjectEntity projectEntity = projectEntityOptional.get();
        Set<UserEntity> currentMembers = projectEntity.getMembers();

        // Validar de qe el usario sea miembro del proyecto, sino lanzamos un USER_NOT_MEMBER
        boolean isMember = currentMembers.contains(memberId);
        if(!isMember) {
            throw BadArgumentExceptionType.USER_NOT_MEMBER.getException();
        }

        UserEntity memberToRemove = memberEntityOptional.get();

        Set<ProjectEntity> memberProjectList =  memberEntityOptional.get().getProjects();
        currentMembers.remove(memberToRemove);
        memberProjectList.remove(projectEntity);

        projectRepository.save(projectEntity);

        Set<ResponseUserDTO> updatedMembersDTO = projectEntity.getMembers().stream()
                .map(userMapper::toResponseUserDTO)
                .collect(Collectors.toSet());

        return updatedMembersDTO;
    }

    //  Listar los usuarios miembros de un proyecto.
    public Set<ResponseUserDTO> getProjectMembers(Long projectId) {

        Optional<ProjectEntity> projectEntityOptional = projectRepository.findById(projectId);
        // Validar que el proyecto exista. Si no existe lanzamos un PROJECT_NOT_FOUND
        if(projectEntityOptional.isEmpty()) {
            throw NotFoundExceptionType.PROJECT_NOT_FOUND.getException();
        }

        Set<UserEntity> projectMembersEntityList = projectEntityOptional.get().getMembers();
        Set<ResponseUserDTO> projectMembersDTOList = projectMembersEntityList.stream()
                .map(userMapper::toResponseUserDTO)
                .collect(Collectors.toSet());

        return projectMembersDTOList;
    }

    // Agregar una nueva lista de tareas dentro de un proyecto.
    public ResponseProjectDTO addTaskListToProject(Long projectId, AddTaskListDTO addTaskListId) {
        Optional<ProjectEntity> projectEntityOptional = projectRepository.findById(projectId);
        Optional<TaskListEntity> taskListEntityOptional = taskListRepository.findById(addTaskListId.getTaskListId());

        // Validar que el proyecto exista. Si no existe lanzamos un PROJECT_NOT_FOUND
        if(projectEntityOptional.isEmpty()) {
            throw NotFoundExceptionType.PROJECT_NOT_FOUND.getException();
        }

        // Validar de que la lista de tareas exista. Si no existe, lanzamos un TASKLIST_NOT_FOUND
        if(taskListEntityOptional.isEmpty()) {
            throw NotFoundExceptionType.TASKLIST_NOT_FOUND.getException();
        }

        ProjectEntity projectEntity = projectEntityOptional.get();
        TaskListEntity taskListEntity = taskListEntityOptional.get();

        // Validamos de que la lista no sea parte del proyecto ya, sino lanzamos un PROJECT_ALREADY_HAS_TASKLIST
        boolean isACurrentTaskList = projectEntity.getTaskLists().contains(taskListEntity);
        if(isACurrentTaskList) {
           throw BadArgumentExceptionType.PROJECT_ALREADY_EXISTS.getException();
        }

        taskListEntity.setProject(projectEntity);
        projectEntity.getTaskLists().add(taskListEntity);

        projectRepository.save(projectEntity);

        ResponseProjectDTO responseProjectDTO = projectMapper.toResponseProjectDTO(projectEntity);
        return responseProjectDTO;
    }


    // Listar las listas de tareas de un proyecto.

    public Set<ResponseTaskListDTO> getProjectTaskLists(Long projectId) {

        // Validar que el proyecto exista. Si no existe lanzamos un PROJECT_NOT_FOUND

        Optional<ProjectEntity> projectEntityOptional = projectRepository.findById(projectId);

        if(projectEntityOptional.isEmpty()) {
            throw NotFoundExceptionType.PROJECT_NOT_FOUND.getException();
        }

        ProjectEntity projectEntity = projectEntityOptional.get();
        Set<TaskListEntity> projectTaskListEntity = projectEntity.getTaskLists();

        Set<ResponseTaskListDTO> projectResponseTaskListDTO = projectTaskListEntity.stream()
                .map(taskListMapper::toResponseTaskListDTO)
                .collect(Collectors.toSet());

        return projectResponseTaskListDTO;
    }

}
