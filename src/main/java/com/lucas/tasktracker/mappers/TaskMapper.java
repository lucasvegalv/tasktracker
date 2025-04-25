package com.lucas.tasktracker.mappers;

import com.lucas.tasktracker.dtos.responses.ResponseTaskDTO;
import com.lucas.tasktracker.entities.TaskEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskEntity toTaskEntity(ResponseTaskDTO responseTaskDTO);
    ResponseTaskDTO toTaskDTO(TaskEntity taskEntity);
    List<ResponseTaskDTO> toTaskDTOList(List<TaskEntity> taskEntities);
}
