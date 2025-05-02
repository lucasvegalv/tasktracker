package com.lucas.tasktracker.services;

import com.lucas.tasktracker.dtos.requests.RequestTaskDTO;
import com.lucas.tasktracker.dtos.responses.ResponseTaskDTO;
import com.lucas.tasktracker.entities.TaskEntity;
import com.lucas.tasktracker.exceptions.BadArgumentExceptionType;
import com.lucas.tasktracker.exceptions.NotFoundException;
import com.lucas.tasktracker.exceptions.NotFoundExceptionType;
import com.lucas.tasktracker.mappers.TaskMapper;
import com.lucas.tasktracker.repositories.TaskRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TaskService {

    TaskRepository taskRepository;
    TaskMapper taskMapper;

    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    // GET /api/tasks/{taskId}: Obtener detalles de una tarea específica.
    public ResponseTaskDTO getTaskById(Long id) {
       Optional<TaskEntity> taskEntityOptional = taskRepository.findById(id);

       if(taskEntityOptional.isEmpty()) {
           throw NotFoundExceptionType.TASK_NOT_FOUND.getException();
       }

       TaskEntity taskEntity = taskEntityOptional.get();
       ResponseTaskDTO responseTaskDTO = taskMapper.toResponseTaskDTO(taskEntity);

       return responseTaskDTO;
    }

    // POST /api/tasks: Crear una nueva tarea
    public ResponseTaskDTO createTask(RequestTaskDTO requestTaskDTO) {
        Optional<TaskEntity> taskEntityOptional = taskRepository.findByTitle(requestTaskDTO.getTitle());

        if(taskEntityOptional.isPresent()) {
            throw BadArgumentExceptionType.DUPLICATE_TASK_NAME.getException();
        }

        TaskEntity taskEntity = taskMapper.requestTaskDTOToTaskEntity(requestTaskDTO);
        taskRepository.save(taskEntity);

        ResponseTaskDTO responseTaskDTO = taskMapper.toResponseTaskDTO(taskEntity);

        return responseTaskDTO;
    }


    // PATCH /api/tasks/{taskId}: Actualizar parcialmente una tarea (ej: estado, prioridad).
    public ResponseTaskDTO updateTask(Long taskId, RequestTaskDTO requestTaskDTO) {
        Optional<TaskEntity> taskEntityOptional = taskRepository.findById(taskId);

        if(taskEntityOptional.isEmpty()) {
            throw NotFoundExceptionType.TASK_NOT_FOUND.getException();
        }

        TaskEntity taskEntity = taskEntityOptional.get();

        if(requestTaskDTO.getTitle() != null) {
            taskEntity.setTitle(requestTaskDTO.getTitle());
        } else if(requestTaskDTO.getDescription() != null) {
            taskEntity.setDescription(requestTaskDTO.getDescription());
        } else if(requestTaskDTO.getPriority() != null) {
            taskEntity.setPriority(requestTaskDTO.getPriority());
        } else if(requestTaskDTO.getStatus() != null) {
            taskEntity.setStatus(requestTaskDTO.getStatus());
        } else if(requestTaskDTO.getDueDate() != null) {
            taskEntity.setDueDate(requestTaskDTO.getDueDate());
        }

        taskRepository.save(taskEntity);

        ResponseTaskDTO responseTaskDTO = taskMapper.toResponseTaskDTO(taskEntity);

        return responseTaskDTO;
    }

    // DELETE /api/tasks/{taskId}: Eliminar una tarea.
    public Set<ResponseTaskDTO> deleteTask(Long id) {
        Optional<TaskEntity> taskEntityOptional = taskRepository.findById(id);

        if(taskEntityOptional.isEmpty()) {
           throw NotFoundExceptionType.TASK_NOT_FOUND.getException();
        }

        taskRepository.deleteById(id);

        List<TaskEntity> taskEntitySet = taskRepository.findAll();
        Set<ResponseTaskDTO> responseTaskDTOSet = taskEntitySet.stream()
                .map(taskMapper::toResponseTaskDTO)
                .collect(Collectors.toSet());

        return responseTaskDTOSet;
    }

}
