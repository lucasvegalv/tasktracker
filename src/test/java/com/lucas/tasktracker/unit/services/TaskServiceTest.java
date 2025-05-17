package com.lucas.tasktracker.unit.services;

import com.lucas.tasktracker.dtos.responses.ResponseTaskDTO;
import com.lucas.tasktracker.entities.TaskEntity;
import com.lucas.tasktracker.exceptions.NotFoundException;
import com.lucas.tasktracker.mappers.TaskMapper;
import com.lucas.tasktracker.repositories.TaskRepository;
import com.lucas.tasktracker.services.TaskService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.lucas.tasktracker.utils.TestEntitiesFactory.getValidTask1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    TaskRepository taskRepository;

    @Mock
    TaskMapper taskMapper;

    @InjectMocks
    TaskService taskService;

    // 1. Task by ID -> Retorna task by ID correctamente
    @Test
    @DisplayName("Should return a user by ID")
    public void shouldReturnUserById() {
        // Given
        TaskEntity task = getValidTask1();
        task.setId(1L);

        ResponseTaskDTO responseTaskDTO = ResponseTaskDTO.builder()
                .title("Primer Tarea")
                .build();

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskMapper.toResponseTaskDTO(task)).thenReturn(responseTaskDTO);

        // When
        ResponseTaskDTO responseTask = taskService.getTaskById(1L);

        // Then
        assertThat(responseTask)
                .isNotNull()
                .extracting(ResponseTaskDTO::getTitle)
                .isEqualTo(responseTaskDTO.getTitle());
    }

    // 1.2. Task by ID -> Lanza NotFoundException si no la encuentra
    @Test
    @DisplayName("Should throw NotFoundException if the task to find does not exists")
    public void shouldThrowNotFoundExceptionIfTaskToFindDoesNotExist() {
        // Given
        Long taskId = 50L;

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NotFoundException.class, () -> taskService.getTaskById(taskId));
    }

    // 2. Create Task -> Crea task correctamente


    // 2.1 Create Task -> Lanza BadArgumentException si ya existe una tarea con el titulo ingresado


    // 3. Update Task -> Actualiza correcetamente la tarea


    // 3.1 Update Task -> Lanza NotFoundException si no la encuentra


    // 4. Delete Task -> Elimina la tarea correctamente


    // 4.1 Delete Task -> Lanza NotFoundException si no la encuentra
}
