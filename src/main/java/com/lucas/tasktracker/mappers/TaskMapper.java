package com.lucas.tasktracker.mappers;

import com.lucas.tasktracker.dtos.TaskDTO;
import com.lucas.tasktracker.entities.TaskEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskEntity toTaskEntity(TaskDTO taskDTO);
    TaskDTO toTaskDTO(TaskEntity taskEntity);
    List<TaskDTO> toTaskDTOList(List<TaskEntity> taskEntities);
}
