package com.lucas.tasktracker.mappers;

import com.lucas.tasktracker.dtos.requests.AddTaskDTO;
import com.lucas.tasktracker.dtos.requests.RequestTaskDTO;
import com.lucas.tasktracker.dtos.responses.ResponseTaskDTO;
import com.lucas.tasktracker.dtos.responses.SimpleResponseTaskListDTO;
import com.lucas.tasktracker.entities.TaskEntity;
import com.lucas.tasktracker.entities.TaskListEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskEntity responseTaskDTOToTaskEntity(ResponseTaskDTO responseTaskDTO);
    TaskEntity requestTaskDTOToTaskEntity(RequestTaskDTO requestTaskDTO);

    @Mapping(source = "id", target = "taskId")
    ResponseTaskDTO toResponseTaskDTO(TaskEntity taskEntity);

    // Metodo personalizado para evitar dependencia circular (SimpleResponseTaskListDTO <--> ResponTaskDTO)
    @Mapping(source= "id", target = "taskListId")
    SimpleResponseTaskListDTO toSimpleResponseTaskListDTO(TaskListEntity taskListEntity);

    @Mapping(source = "taskId", target = "id")
    TaskEntity addTaskDTOToEntity(AddTaskDTO addTaskDTO);
}
