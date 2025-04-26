package com.lucas.tasktracker.mappers;

import com.lucas.tasktracker.dtos.requests.RequestTaskDTO;
import com.lucas.tasktracker.dtos.responses.ResponseTaskDTO;
import com.lucas.tasktracker.entities.TaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {TaskListMapper.class})
public interface TaskMapper {
    TaskEntity responseTaskDTOToTaskEntity(ResponseTaskDTO responseTaskDTO);
    TaskEntity requestTaskDTOToTaskEntity(RequestTaskDTO requestTaskDTO);

    @Mapping(source = "id", target = "taskId")
    ResponseTaskDTO toResponseTaskDTO(TaskEntity taskEntity);
    List<ResponseTaskDTO> toTaskDTOList(List<TaskEntity> taskEntities);
}
