package com.lucas.tasktracker.mappers;

import com.lucas.tasktracker.dtos.requests.RequestUserDTO;
import com.lucas.tasktracker.dtos.responses.ResponseUserDTO;
import com.lucas.tasktracker.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserEntity toUserEntity(RequestUserDTO userDTO);
    RequestUserDTO toRequestUserDTO(UserEntity userEntity);

    @Mapping(source = "id", target = "userId")
    ResponseUserDTO toResponseUserDTO(UserEntity userEntity);
    List<ResponseUserDTO> toResponseUserDTOList(List<UserEntity> userEntities);
}
