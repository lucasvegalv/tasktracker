package com.lucas.tasktracker.services;

import com.lucas.tasktracker.dtos.requests.RequestUserDTO;
import com.lucas.tasktracker.dtos.responses.ResponseProjectDTO;
import com.lucas.tasktracker.dtos.responses.ResponseUserDTO;
import com.lucas.tasktracker.entities.ProjectEntity;
import com.lucas.tasktracker.entities.UserEntity;
import com.lucas.tasktracker.mappers.ProjectMapper;
import com.lucas.tasktracker.mappers.UserMapper;
import com.lucas.tasktracker.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    UserRepository userRepository;
    UserMapper userMapper;
    ProjectMapper projectMapper;
    RequestUserDTO requestUserDTO;
    ResponseUserDTO responseUserDTO;

    public UserService(UserRepository userRepository, UserMapper userMapper, ProjectMapper projectMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.projectMapper = projectMapper;
    }

    // Crear un nuevo usuario.
    @Transactional
    public ResponseUserDTO createUser(RequestUserDTO requestUserDTO) {

        UserEntity userEntity = userMapper.toUserEntity(requestUserDTO);
        UserEntity savedUserEntity =  userRepository.save(userEntity);

        System.out.println(savedUserEntity.toString());
        System.out.println("---------------------");

        ResponseUserDTO responseUserDTO = userMapper.toResponseUserDTO(savedUserEntity);
        System.out.println(responseUserDTO.toString());
        System.out.println("---------------------");

        return responseUserDTO;
    }

    // Obtener lista paginada y ordenada de usuarios.
    public List<ResponseUserDTO> getUsers() {
        List<UserEntity> userEntityList = userRepository.findAll();
        List<ResponseUserDTO> responseUserDTOList = userMapper.toResponseUserDTOList(userEntityList);

        return responseUserDTOList;
    }

    // Obtener detalles de un usuario específico.
    public Optional<ResponseUserDTO> getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toResponseUserDTO);
    }


    // Actualizar un usuario existente.
    @Transactional
    public Optional<ResponseUserDTO> updateUser(Long id, RequestUserDTO userDTO) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(id);

        // Si lo encontramos, validamos que campo/s quiere actualizar
        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();

            if(userDTO.getUsername() != null) {
                userEntity.setUsername(userDTO.getUsername());
            }
            if(userDTO.getLastname() != null) {
                userEntity.setLastname(userDTO.getLastname());
            }
            if(userDTO.getJob() != null) {
                userEntity.setJob(userDTO.getJob());
            }

            userRepository.save(userEntity);
            ResponseUserDTO updatedUserDTO = userMapper.toResponseUserDTO(userEntity);
            return Optional.of(updatedUserDTO);
        }
        return Optional.empty();
    }



    // Eliminar un usuario.
    public List<ResponseUserDTO> deleteUser(Long user_id) {
        userRepository.deleteById(user_id);
        List<ResponseUserDTO> responseUserDTOList = userMapper.toResponseUserDTOList(userRepository.findAll());

        return responseUserDTOList;
    }



    // Listar los proyectos a los que pertenece un usuario.

    public Optional<Set<ResponseProjectDTO>> getUserProjects(Long user_id) {
        // 1. Encontrar al usuario por su id
        Optional<UserEntity> userEntityOptional = userRepository.findById(user_id);

        if(userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();

            // 2. Con ese id, accedemos a su listado de proyectos
            Set<ProjectEntity> userProjectsEntity = userEntity.getProjects();
            Set<ResponseProjectDTO> userResponseProjectsDTOs = userProjectsEntity.stream()
                    .map(projectMapper::toResponseProjectDTO)
                    .collect(Collectors.toSet());

            return Optional.of(userResponseProjectsDTOs);


        }
        return Optional.empty();
    }



}
