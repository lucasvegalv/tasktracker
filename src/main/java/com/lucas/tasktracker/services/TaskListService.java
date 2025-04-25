package com.lucas.tasktracker.services;

import com.lucas.tasktracker.dtos.requests.RequestTaskListDTO;
import com.lucas.tasktracker.dtos.responses.ResponseTaskListDTO;
import com.lucas.tasktracker.entities.ProjectEntity;
import com.lucas.tasktracker.entities.TaskListEntity;
import com.lucas.tasktracker.mappers.TaskListMapper;
import com.lucas.tasktracker.repositories.ProjectRepository;
import com.lucas.tasktracker.repositories.TaskListRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TaskListService {


    private final ProjectRepository projectRepository;
    TaskListRepository taskListRepository;
    TaskListMapper taskListMapper;

    public TaskListService(TaskListRepository taskListRepository, TaskListMapper taskListMapper, ProjectRepository projectRepository) {
        this.taskListRepository = taskListRepository;
        this.taskListMapper = taskListMapper;
        this.projectRepository = projectRepository;
    }

//    GET /api/tasklists: Obtener todas las listas de tareas.
    public Optional<List<ResponseTaskListDTO>> getAllTaskLists() {
        List<TaskListEntity> taskListEntities = taskListRepository.findAll();
        List<ResponseTaskListDTO> responseTaskListsDTOs = taskListEntities.stream()
                .map(taskListMapper::toResponseTaskListDTO)
                .collect(Collectors.toList());


        return Optional.of(responseTaskListsDTOs);
    }

//    GET /api/tasklists/{taskListId}: Obtener detalles de una lista de tareas específica.
    public Optional<ResponseTaskListDTO> getTaskListById(Long taskListId) {
        Optional<TaskListEntity> taskListEntityOptional = taskListRepository.findById(taskListId);

        if(taskListEntityOptional.isEmpty()) {
            return Optional.empty();
        }

        TaskListEntity taskListEntity = taskListEntityOptional.get();
        ResponseTaskListDTO responseTaskListDTO = taskListMapper.toResponseTaskListDTO(taskListEntity);

        return Optional.of(responseTaskListDTO);
    }


//    DELETE /api/tasklists/{taskListId}: Eliminar una lista de tareas.
    @Transactional
    public Optional<Set<ResponseTaskListDTO>> deleteTaskListById(Long taskListId) {
        Optional<TaskListEntity> taskListEntityOptional = taskListRepository.findById(taskListId);

        if(taskListEntityOptional.isEmpty()) {
            return Optional.empty();
        }

        taskListRepository.deleteById(taskListId);

        List<TaskListEntity> taskListEntities = taskListRepository.findAll();
        Set<ResponseTaskListDTO> responseTaskListDTOS = taskListEntities.stream()
                .map(taskListMapper::toResponseTaskListDTO)
                .collect(Collectors.toSet());

        return Optional.of(responseTaskListDTOS);
    }



    // Crear una nueva lista de tareas
    public Optional<ResponseTaskListDTO> createTaskList(RequestTaskListDTO requestTaskListDTO) {

        TaskListEntity taskListEntity = new TaskListEntity();
        taskListEntity.setDescription(requestTaskListDTO.getDescription());
        taskListEntity.setName(requestTaskListDTO.getName());

        taskListRepository.save(taskListEntity);


        ResponseTaskListDTO responseTaskListDTO = taskListMapper.toResponseTaskListDTO(taskListEntity);

        return Optional.of(responseTaskListDTO);
    }

//    PUT /api/tasklists/{taskListId}: Actualizar una lista de tareas.
    public Optional<ResponseTaskListDTO> updateTaskList(Long taskListId, RequestTaskListDTO requestTaskListDTO) {
        Optional<TaskListEntity> taskListEntityOptional = taskListRepository.findById(taskListId);

        if(taskListEntityOptional.isEmpty()) {
            return Optional.empty();
        }

        TaskListEntity taskListEntity = taskListEntityOptional.get();

        if(requestTaskListDTO.getName() != null) {
            taskListEntity.setName(requestTaskListDTO.getName());
        }

        if(requestTaskListDTO.getDescription() != null) {
            taskListEntity.setDescription(requestTaskListDTO.getDescription());
        }

        TaskListEntity updatedTaskList = taskListRepository.save(taskListEntity);
        ResponseTaskListDTO responseTaskListDTO = taskListMapper.toResponseTaskListDTO(updatedTaskList);

        return Optional.of(responseTaskListDTO);
    }

}
