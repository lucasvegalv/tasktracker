package com.lucas.tasktracker.mappers;

import com.lucas.tasktracker.dtos.requests.RequestProjectDTO;
import com.lucas.tasktracker.dtos.responses.ResponseProjectDTO;
import com.lucas.tasktracker.dtos.responses.SimpleResponseProjectDTO;
import com.lucas.tasktracker.entities.ProjectEntity;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
    ProjectEntity toProjectEntity(SimpleResponseProjectDTO simpleResponseProjectDTO);
    ProjectEntity toProjectEntity(ResponseProjectDTO responseProjectDTO);
    ProjectEntity toProjectEntity(RequestProjectDTO requestProjectDTO);
    SimpleResponseProjectDTO toSimpleResponseProjectDTO(ProjectEntity projectEntity);
    ResponseProjectDTO toResponseProjectDTO(ProjectEntity projectEntity);
    List<SimpleResponseProjectDTO> toProjectDTOList(List<ProjectEntity> projectEntities);
}
