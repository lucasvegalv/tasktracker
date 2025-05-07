package com.lucas.tasktracker.unit.repositories;

import com.lucas.tasktracker.entities.UserEntity;
import com.lucas.tasktracker.repositories.UserRepository;
import org.apache.catalina.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import static com.lucas.tasktracker.utils.TestUserFactory.*;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@EntityScan("com.lucas.tasktracker.entities")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    //private final static UserEntity first_user = getValidUser1();
    //private final static  UserEntity second_user = getValidUser2();
    //private final static  UserEntity invalid_user = getInvalidUser();

    @Test
    @DisplayName("Should create a user if inputs are valid")
    public void shouldCreateAUser(){
        // Given
        UserEntity user = getValidUser1();

        // When
        UserEntity savedUser = userRepository.save(user);

        // Then
        assertThat(savedUser.getId()).isNotNull();
    }

    @Test
    @DisplayName("Shouldn't create a user if the inputs are invalid")
    public void shouldNotCreateAUser(){
        // Given
        UserEntity invalidUser = getInvalidUser();

        // When
        UserEntity savedUser = userRepository.save(invalidUser);

        // Then
        assertThat(savedUser.getId()).isNull();
    }

    @Test
    @DisplayName("Should find a user by his/her id")
    public void shouldFindByUserId(){
        // Given
        UserEntity user = getValidUser1();

        userRepository.save(user);

        // When
        Optional<UserEntity> searchedUser = userRepository.findById(user.getId());

        // Then
        assertThat(searchedUser)
                .isPresent() // Validamos de que no traiga null
                .get() // Obtenemos el optional
                .extracting(UserEntity::getId, UserEntity::getUsername)
                .containsExactly(user.getId(), user.getUsername());
    }

    @Test
    @DisplayName("Should return all users")
    public void shouldFindAllUsers(){
        // Given
        UserEntity user1 = getValidUser1();
        UserEntity user2 = getValidUser2();

        // When
        userRepository.save(user1);
        userRepository.save(user2);

        List<UserEntity> users = userRepository.findAll();

        // Then
        assertThat(users)
                .isNotEmpty()
                .hasSize(2)
                .contains(user1, user2);
    }




    @Test
    @DisplayName("Should update a user if the inputs are valid")
    public void shouldUpdateAUser(){
        // Given

        // When

        // Then
    }

    @Test
    @DisplayName("Shouldn't update a user if the inputs are invalid")
    public void shouldNotUpdateAUser(){
        // Given

        // When

        // Then
    }

    @Test
    @DisplayName("Should delete a user if the user exists")
    public void shouldDeleteAUserIfExist(){
        // Given

        // When

        // Then
    }

    @Test
    @DisplayName("Shouldn't delete a user if the user does not exists")
    public void shouldNotDeleteAUserIfNotExist(){
        // Given

        // When

        // Then
    }

}
