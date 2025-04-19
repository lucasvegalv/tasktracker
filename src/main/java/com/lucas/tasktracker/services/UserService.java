package com.lucas.tasktracker.services;

import com.lucas.tasktracker.dtos.UserDTO;
import com.lucas.tasktracker.entities.UserEntity;
import com.lucas.tasktracker.mappers.UserMapper;
import com.lucas.tasktracker.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    UserRepository userRepository;
    UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    // POST /api/users: Crear un nuevo usuario.
    public UserDTO crearUsuario(UserDTO userDTO) {

        UserEntity userEntity = userMapper.toUserEntity(userDTO);
        UserEntity savedUserEntity =  userRepository.save(userEntity);

        System.out.println(savedUserEntity.toString());
        System.out.println("---------------------");

        UserDTO responseUserDTO = userMapper.toUserDTO(savedUserEntity);
        System.out.println(responseUserDTO.toString());
        System.out.println("---------------------");

        return responseUserDTO;
    }

    // GET /api/users: Obtener lista paginada y ordenada de usuarios.
    public List<UserDTO> getUsers() {
        List<UserEntity> userEntityList = userRepository.findAll();
        List<UserDTO> userDTOList = userMapper.toUserDTOList(userEntityList);

        return userDTOList;
    }

    // GET /api/users/{userId}: Obtener detalles de un usuario específico.
    public Optional<UserDTO> getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toUserDTO);
    }


    // PATCH /api/users/{userId}: Actualizar un usuario existente.
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



    // DELETE /api/users/{userId}: Eliminar un usuario.
    public List<UserDTO> deleteUser(Long user_id) {
        userRepository.deleteById(user_id);
        List<UserDTO> userDTOList = userMapper.toUserDTOList(userRepository.findAll());

        return userDTOList;
    }



    // GET /api/users/{userId}/projects: Listar los proyectos a los que pertenece un usuario.

}
