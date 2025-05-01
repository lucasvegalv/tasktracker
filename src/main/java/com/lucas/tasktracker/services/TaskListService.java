package com.lucas.tasktracker.services;

import com.lucas.tasktracker.dtos.requests.AddTaskDTO;
import com.lucas.tasktracker.dtos.requests.RequestTaskListDTO;
import com.lucas.tasktracker.dtos.responses.ResponseTaskDTO;
import com.lucas.tasktracker.dtos.responses.ResponseTaskListDTO;
import com.lucas.tasktracker.entities.ProjectEntity;
import com.lucas.tasktracker.entities.TaskEntity;
import com.lucas.tasktracker.entities.TaskListEntity;
import com.lucas.tasktracker.mappers.TaskListMapper;
import com.lucas.tasktracker.mappers.TaskMapper;
import com.lucas.tasktracker.repositories.ProjectRepository;
import com.lucas.tasktracker.repositories.TaskListRepository;
import com.lucas.tasktracker.repositories.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TaskListService {


    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    TaskListRepository taskListRepository;
    TaskListMapper taskListMapper;
    TaskMapper taskMapper;

    public TaskListService(TaskListRepository taskListRepository, TaskListMapper taskListMapper, ProjectRepository projectRepository, TaskMapper taskMapper, TaskRepository taskRepository, ErrorMvcAutoConfiguration errorMvcAutoConfiguration) {
        this.taskListRepository = taskListRepository;
        this.taskListMapper = taskListMapper;
        this.projectRepository = projectRepository;
        this.taskMapper = taskMapper;
        this.taskRepository = taskRepository;
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


    //    POST /api/tasklists/{taskListId}/tasks: Agregar una tarea a una lista de tareas
    public Optional<Set<ResponseTaskDTO>> addTaskToTaskList (Long taskListId, AddTaskDTO addTaskDTO) {
        Optional<TaskListEntity> taskListEntityOptional = taskListRepository.findById(taskListId);
        Optional<TaskEntity> taskEntityOptional = taskRepository.findById(addTaskDTO.getTaskId());


        if(taskListEntityOptional.isEmpty() || taskEntityOptional.isEmpty()) {
            return Optional.empty();
        }

        TaskListEntity taskListEntity = taskListEntityOptional.get();
        TaskEntity taskEntity = taskEntityOptional.get();

        // Asociamos ambos lados bidireccionales en memoria
        taskListEntity.getTasks().add(taskEntity);
        taskEntity.setTaskList(taskListEntity); // Por no tener esta linea, antes me daba el null de taskEntity.getTaskList() y tiene sentido... no lo estaba setteando

        // Hacemos save del lado padre/dueno
        taskListRepository.save(taskListEntity);

         Set<TaskEntity> taskListEntityTasks = taskListEntity.getTasks();

         // Printeamos a la task y y su tasklist para ver como le llegan al mapper
        for(TaskEntity taskEntityTask : taskListEntityTasks) {
            System.out.println("----------------------------------------------");
            System.out.println(taskEntityTask.getId());
            System.out.println("TaskList ID" + taskEntityTask.getTaskList().getId());
            System.out.println("TaskList ID" +  taskEntityTask.getTaskList().getName());
            System.out.println("----------------------------------------------");
        }

         Set<ResponseTaskDTO> responseTaskDTOS = taskListEntityTasks.stream()
                 .map(taskMapper::toResponseTaskDTO)
                 .collect(Collectors.toSet());

         return Optional.of(responseTaskDTOS);

    }

}
