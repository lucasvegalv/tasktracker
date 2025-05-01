package com.lucas.tasktracker.mappers;

import com.lucas.tasktracker.dtos.responses.ResponseTaskListDTO;
import com.lucas.tasktracker.dtos.responses.SimpleResponseTaskListDTO;
import com.lucas.tasktracker.entities.TaskListEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {TaskMapper.class})
public interface TaskListMapper {
    TaskListEntity toTaskListEntity(ResponseTaskListDTO responseTaskListDTO);

    @Mapping(source = "id", target = "taskListId")
    ResponseTaskListDTO toResponseTaskListDTO(TaskListEntity taskListEntity);

    @Mapping(source = "id", target = "taskListId")
    SimpleResponseTaskListDTO toSimpleResponseTaskListDTO(TaskListEntity taskListEntity);

}
