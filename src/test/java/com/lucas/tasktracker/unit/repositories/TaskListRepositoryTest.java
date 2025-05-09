package com.lucas.tasktracker.unit.repositories;

import com.lucas.tasktracker.entities.TaskListEntity;
import com.lucas.tasktracker.repositories.TaskListRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.lucas.tasktracker.utils.TestEntitiesFactory.getValidTaskList1;
import static com.lucas.tasktracker.utils.TestEntitiesFactory.getValidTaskList2;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@EntityScan("com.lucas.tasktracker.entities")
public class TaskListRepositoryTest {

    @Autowired
    private TaskListRepository repository;


    // Create
    @Test
    public void shouldCreateTaskList() {
        TaskListEntity taskListEntity = getValidTaskList1();
        repository.save(taskListEntity);

        Optional<TaskListEntity> savedTaskList = repository.findById(taskListEntity.getId());

        assertThat(savedTaskList)
                .isPresent();
    }

    // Read All
    @Test
    public void shouldFindAllTaskLists() {
        TaskListEntity taskListEntity = getValidTaskList1();
        TaskListEntity taskListEntity2 = getValidTaskList2();
        repository.save(taskListEntity);
        repository.save(taskListEntity2);

        List<TaskListEntity> savedTaskLists = repository.findAll();

        assertThat(savedTaskLists)
                .hasSize(2)
                .contains(taskListEntity, taskListEntity2);
    }

    // Read by ID
    @Test
    public void shouldFindTaskListById() {
        TaskListEntity taskListEntity = getValidTaskList1();
        repository.save(taskListEntity);

        Optional<TaskListEntity> savedTaskList = repository.findById(taskListEntity.getId());

        assertThat(savedTaskList)
                .get()
                .extracting(TaskListEntity::getName)
                .isEqualTo("Desarrollar tests");
    }

    // Read by name -> Query Method
    @Test
    public void shouldFindTaskListByName() {
        TaskListEntity taskListEntity = getValidTaskList1();
        repository.save(taskListEntity);

        Optional<TaskListEntity> savedTaskList = repository.findByName(taskListEntity.getName());

        assertThat(savedTaskList)
                .get()
                .extracting(TaskListEntity::getName)
                .isEqualTo("Desarrollar tests");
    }

    // Delete
    @Test
    public void shouldDeleteTaskList() {
        TaskListEntity taskListEntity = getValidTaskList1();
        repository.save(taskListEntity);

        repository.delete(taskListEntity);
        Optional<TaskListEntity> deletedTaskList = repository.findById(taskListEntity.getId());

        assertThat(deletedTaskList)
                .isEmpty();
    }

    // Update
    @Test
    public void shouldUpdateTaskList() {
        TaskListEntity taskListEntity = getValidTaskList1();
        repository.save(taskListEntity);

        taskListEntity.setName("Desarrollar y optimizar los tests");
        repository.save(taskListEntity);

        Optional<TaskListEntity> savedTaskList = repository.findById(taskListEntity.getId());

        assertThat(savedTaskList)
                .get()
                .extracting(TaskListEntity::getName)
                .isEqualTo("Desarrollar y optimizar los tests");
    }



}
