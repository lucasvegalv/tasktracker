package com.lucas.tasktracker.services;

import com.lucas.tasktracker.dtos.ProjectDTO;
import com.lucas.tasktracker.dtos.UserDTO;
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

    public UserService(UserRepository userRepository, UserMapper userMapper, ProjectMapper projectMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    // Crear un nuevo usuario.
    @Transactional
    public UserDTO createUser(UserDTO userDTO) {

        UserEntity userEntity = userMapper.toUserEntity(userDTO);
        UserEntity savedUserEntity =  userRepository.save(userEntity);

        System.out.println(savedUserEntity.toString());
        System.out.println("---------------------");

        UserDTO responseUserDTO = userMapper.toUserDTO(savedUserEntity);
        System.out.println(responseUserDTO.toString());
        System.out.println("---------------------");

        return responseUserDTO;
    }

    // Obtener lista paginada y ordenada de usuarios.
    public List<UserDTO> getUsers() {
        List<UserEntity> userEntityList = userRepository.findAll();
        List<UserDTO> userDTOList = userMapper.toUserDTOList(userEntityList);

        return userDTOList;
    }

    // Obtener detalles de un usuario específico.
    public Optional<UserDTO> getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toUserDTO);
    }


    // Actualizar un usuario existente.
    @Transactional
    public Optional<UserDTO> updateUser(Long id, UserDTO userDTO) {
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
            UserDTO updatedUserDTO = userMapper.toUserDTO(userEntity);
            return Optional.of(updatedUserDTO);
        }
        return Optional.empty();
    }



    // Eliminar un usuario.
    public List<UserDTO> deleteUser(Long user_id) {
        userRepository.deleteById(user_id);
        List<UserDTO> userDTOList = userMapper.toUserDTOList(userRepository.findAll());

        return userDTOList;
    }



    // Listar los proyectos a los que pertenece un usuario.

    public Optional<Set<ProjectDTO>> getUserProjects(Long user_id) {
        // 1. Encontrar al usuario por su id
        Optional<UserEntity> userEntityOptional = userRepository.findById(user_id);

        if(userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();

            // 2. Con ese id, accedemos a su listado de proyectos
            Set<ProjectEntity> userProjectsEntity = userEntity.getProjects();
            Set<ProjectDTO> userProjectDTOSet = userProjectsEntity.stream()
                    .map(projectMapper::toProjectDTO)
                    .collect(Collectors.toSet());

            return Optional.of(userProjectDTOSet);
        }
        return Optional.empty();
    }



}
