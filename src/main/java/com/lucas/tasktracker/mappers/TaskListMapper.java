package com.lucas.tasktracker.mappers;

import com.lucas.tasktracker.dtos.TaskListDTO;
import com.lucas.tasktracker.entities.TaskListEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskListMapper {
    TaskListEntity toTaskListEntity(TaskListDTO taskListDTO);
    TaskListDTO toTaskListDTO(TaskListEntity taskListEntity);
}
