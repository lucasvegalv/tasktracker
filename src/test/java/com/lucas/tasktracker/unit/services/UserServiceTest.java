package com.lucas.tasktracker.unit.services;

import com.lucas.tasktracker.dtos.requests.RequestUserDTO;
import com.lucas.tasktracker.dtos.responses.ResponseProjectDTO;
import com.lucas.tasktracker.dtos.responses.ResponseUserDTO;
import com.lucas.tasktracker.entities.ProjectEntity;
import com.lucas.tasktracker.entities.UserEntity;
import com.lucas.tasktracker.enumerations.JobsEnum;
import com.lucas.tasktracker.exceptions.NotFoundException;
import com.lucas.tasktracker.mappers.ProjectMapper;
import com.lucas.tasktracker.mappers.UserMapper;
import com.lucas.tasktracker.repositories.UserRepository;
import com.lucas.tasktracker.services.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.lucas.tasktracker.utils.TestEntitiesFactory.getValidUser1;
import static com.lucas.tasktracker.utils.TestEntitiesFactory.getValidUser2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    Logger logger = LoggerFactory.getLogger(UserServiceTest.class.getName());

    @Mock
    UserRepository userRepository;

    @Mock
    UserMapper userMapper;

    @Mock
    ProjectMapper projectMapper;

    @InjectMocks
    UserService userService;

    // 1. Create User Test
        // Escenario 1: Recibe un RequestUserDTO y lo guarda

    @Test
    @DisplayName("Should create a user")
    public void shouldCreateUser() {
        // Given
        RequestUserDTO requestUserDTO = RequestUserDTO.builder()
                .username("Elon")
                .lastname("Gates")
                .job(JobsEnum.SOFTWARE_ENGINEER)
                .build();
        logger.info("User created -> {}", requestUserDTO.getUsername());

        ResponseUserDTO expectedResponse = ResponseUserDTO.builder()
                        .username("Elon")
                        .lastname("Gates")
                        .job(JobsEnum.SOFTWARE_ENGINEER)
                        .build();

        UserEntity userEntity = getValidUser1();


        when(userMapper.toUserEntity(requestUserDTO)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(userMapper.toResponseUserDTO(userEntity)).thenReturn(expectedResponse);


        // When
        ResponseUserDTO createdUser = userService.createUser(requestUserDTO);
        logger.warn("Tryng another WARN log");
        logger.warn("Another one!");
        logger.trace("Finally an error log");

        // Then
        assertThat(createdUser)
                .extracting(ResponseUserDTO::getUsername, ResponseUserDTO::getLastname, ResponseUserDTO::getJob)
                .containsExactly("Elon", "Gates", JobsEnum.SOFTWARE_ENGINEER);
    }


    // 2. Get Users Test
        // Escenario 1: Si agregamos a dos usuarios, debe retornarlos
    @Test
    @DisplayName("Should find all users")
    public void shouldFindAllUsers() {
        // Given
        UserEntity userEntity = getValidUser1();
        UserEntity userEntity2 = getValidUser2();
        List<UserEntity> userEntityList = List.of(userEntity, userEntity2);

        ResponseUserDTO responseUserDTO = ResponseUserDTO.builder()
                        .username("Elon")
                        .lastname("Gates")
                        .job(JobsEnum.SOFTWARE_ENGINEER)
                        .build();

        ResponseUserDTO responseUserDTO2 = ResponseUserDTO.builder()
                .username("Javier")
                .lastname("Messi")
                .job(JobsEnum.DESIGNER)
                .build();


        when(userRepository.findAll()).thenReturn(userEntityList);
        when(userMapper.toResponseUserDTOList(userEntityList)).thenReturn(List.of(responseUserDTO, responseUserDTO2));

        // When
        List<ResponseUserDTO> returnedUsers = userService.getUsers();

        // Then
        assertThat(returnedUsers)
                .isNotEmpty()
                .hasSize(2)
                .contains(responseUserDTO, responseUserDTO2);

    }


    // 3. Get User By ID Test
        // Escenario 1: Buscamos un id que existe y lo retornamos
    @Test
    @DisplayName("Should find user by id")
    public void shouldFindUserById() {
        // Given
        UserEntity userEntity = UserEntity.builder()
                        .id(1L)
                        .username("Elon")
                        .lastname("Musk")
                        .job(JobsEnum.PROJECT_MANAGER)
                        .build();

        ResponseUserDTO responseUserDTO = ResponseUserDTO.builder()
                        .userId(1L)
                        .username("Elon")
                        .lastname("Musk")
                        .job(JobsEnum.PROJECT_MANAGER)
                        .build();


        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        when(userMapper.toResponseUserDTO(userEntity)).thenReturn(responseUserDTO);

        // When
        ResponseUserDTO returnedUser = userService.getUserById(1L);

        // Then
        assertThat(returnedUser)
                .isNotNull()
                .extracting(ResponseUserDTO::getUsername, ResponseUserDTO::getLastname, ResponseUserDTO::getJob)
                .containsExactly("Elon", "Musk", JobsEnum.PROJECT_MANAGER);

    }


        // Escenario 2: Buscamos un id que *no* existe y lanza una NotFoundExceptionType.USER_NOT_FOUND
    @Test
    @DisplayName("Should throw a NotFoundException if the user does not exist")
    public void shouldThrowUserNotFoundExceptionIfUserDoesNotExist() {
        // Given
        Long userId = 50L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NotFoundException.class, () -> userService.getUserById(userId));
    }

    // 4. Update User Test
        // Escenario 1: Actualizamos a un usuario existente y lo guarda correctamente
    @Test
    @DisplayName("Should udpate user")
    public void shouldUpdateUser() {
        // Given
        UserEntity userEntity = UserEntity.builder()
                .id(1L)
                .username("Lucas")
                .lastname("Vega")
                .job(JobsEnum.SOFTWARE_ENGINEER)
                .build();

        UserEntity updatedUser = UserEntity.builder()
                .id(1L)
                .username("Lucas")
                .lastname("Vega")
                .job(JobsEnum.DESIGNER)
                .build();

        RequestUserDTO requestUserDTO = RequestUserDTO.builder()
                        .job(JobsEnum.DESIGNER)
                        .build();

        ResponseUserDTO responseUserDTO = ResponseUserDTO.builder()
                .userId(1L)
                .username("Lucas")
                .lastname("Vega")
                .job(JobsEnum.DESIGNER)
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        when(userRepository.save(userEntity)).thenReturn(updatedUser);
        when(userMapper.toResponseUserDTO(updatedUser)).thenReturn(responseUserDTO);

        // When
        ResponseUserDTO updatedUserDTO = userService.updateUser(1L, requestUserDTO);

        // Then
        assertThat(updatedUserDTO)
                .isNotNull()
                .extracting(ResponseUserDTO::getUsername, ResponseUserDTO::getLastname, ResponseUserDTO::getJob)
                .containsExactly("Lucas", "Vega", JobsEnum.DESIGNER);
    }

        // Escenario 2: Intentamos actualizar un usuario que no existe y lanza NotFoundExceptionType.USER_NOT_FOUND
    @Test
    @DisplayName("Should throw a NotFoundException if the user to be updated does not exists")
    public void shouldThrowUserNotFoundExceptionIfUserToUpdateDoesNotExist() {
        // Given
        Long userId = 50L;
        RequestUserDTO requestUserDTO = RequestUserDTO.builder().username("Lucas").build();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NotFoundException.class, () ->userService.updateUser(userId, requestUserDTO));
    }


    // 5. Delete User Test
        // Escenario 1: Eliminamos a un usuario existente correctamente
    @Test
    @DisplayName("Should delete a user")
    public void shouldDeleteUser() {
        // Given
        UserEntity user = getValidUser1();
        user.setId(1L);

        UserEntity remainingUser = getValidUser2();
        List<UserEntity> remainingUsers = List.of(remainingUser);

        ResponseUserDTO expectedResponse = ResponseUserDTO.builder()
                .username("Javier")
                .lastname("Messi")
                .job(JobsEnum.DESIGNER)
                .build();

        List<ResponseUserDTO> responseUserDTOList = List.of(expectedResponse);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).deleteById(1L);
        when(userRepository.findAll()).thenReturn(remainingUsers);
        when(userMapper.toResponseUserDTOList(remainingUsers)).thenReturn(responseUserDTOList);

        // When
        List<ResponseUserDTO> responseUserDTOs = userService.deleteUser(1L);

        // Then
        assertThat(responseUserDTOs)
                .hasSize(1)
                .extracting(ResponseUserDTO::getUsername, ResponseUserDTO::getLastname, ResponseUserDTO::getJob)
                .containsExactly(tuple("Javier", "Messi", JobsEnum.DESIGNER));

        verify(userRepository).deleteById(1L);
    }

        // Escenario 2: Intentamos eliminar a un usuario que no existe y lanza NotFoundExceptionType.USER_NOT_FOUND
    @Test
    @DisplayName("Should throw a NotFoundException if the user to be deleted does not exists")
    public void shouldThrowUserNotFoundExceptionIfUserToDeleteDoesNotExist() {
        Long userId = 50L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.deleteUser(userId));
    }

    // 6. Get User Projects Test
        // Escenario 1: Buscamos los proyectos de un usuario existente y los devuelve
    @Test
    @DisplayName("Should return all the projects of an existing user")
    public void shouldReturnAllProjectsOfExistingUser() {
        // Given
        ProjectEntity projectEntity = ProjectEntity.builder()
                .id(1L)
                .title("Test Project")
                .build();

        Long userId = 1L;
        UserEntity user = getValidUser1();
        user.setId(userId);
        user.setProjects(Set.of(projectEntity));


        ResponseProjectDTO responseProjectDTO = ResponseProjectDTO.builder()
                .projectId(1L)
                .title("Test Project")
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(projectMapper.toResponseProjectDTO(projectEntity)).thenReturn(responseProjectDTO);

        // When
        Set<ResponseProjectDTO> responseProjectDTOS = userService.getUserProjects(userId);

        // Then
        assertThat(responseProjectDTOS)
                .hasSize(1)
                .extracting(ResponseProjectDTO::getProjectId, ResponseProjectDTO::getTitle)
                .containsExactly(tuple(1L, "Test Project"));
    }


        // Escenario 2: Buscamos los proyectos de un usuario inexistente y lanza NotFoundExceptionType.USER_NOT_FOUND
    @Test
    @DisplayName("Should throw a NotFoundException if the user owner of the projects does not exists")
    public void shouldThrowUserNotFoundExceptionIfUserOwnerDoesNotExist() {
        Long userId = 50L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.deleteUser(userId));
    }
}
