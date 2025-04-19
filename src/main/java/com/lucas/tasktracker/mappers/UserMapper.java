package com.lucas.tasktracker.mappers;

import com.lucas.tasktracker.dtos.UserDTO;
import com.lucas.tasktracker.entities.UserEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserEntity toUserEntity(UserDTO userDTO);
    UserDTO toUserDTO(UserEntity userEntity);
    List<UserDTO> toUserDTOList(List<UserEntity> userEntities);
}
