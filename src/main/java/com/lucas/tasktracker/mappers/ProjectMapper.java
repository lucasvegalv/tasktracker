package com.lucas.tasktracker.mappers;

import com.lucas.tasktracker.dtos.ProjectDTO;
import com.lucas.tasktracker.entities.ProjectEntity;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
    ProjectEntity toProjectEntity(ProjectDTO projectDTO);
    ProjectDTO toProjectDTO(ProjectEntity projectEntity);
    List<ProjectDTO> toProjectDTOList(List<ProjectEntity> projectEntities);
}
