package com.lucas.tasktracker.mappers;

import com.lucas.tasktracker.dtos.requests.RequestProjectDTO;
import com.lucas.tasktracker.dtos.responses.ResponseProjectDTO;
import com.lucas.tasktracker.dtos.responses.SimpleResponseProjectDTO;
import com.lucas.tasktracker.entities.ProjectEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {TaskListMapper.class, UserMapper.class})
public interface ProjectMapper {
    ProjectEntity toProjectEntity(SimpleResponseProjectDTO simpleResponseProjectDTO);
    ProjectEntity toProjectEntity(ResponseProjectDTO responseProjectDTO);
    ProjectEntity toProjectEntity(RequestProjectDTO requestProjectDTO);

    @Mapping(source = "id", target = "projectId")
    SimpleResponseProjectDTO toSimpleResponseProjectDTO(ProjectEntity projectEntity);

    @Mapping(source = "id", target = "projectId")
    ResponseProjectDTO toResponseProjectDTO(ProjectEntity projectEntity);
    List<SimpleResponseProjectDTO> toProjectDTOList(List<ProjectEntity> projectEntities);
}
