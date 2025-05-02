package com.lucas.tasktracker.repositories;

import com.lucas.tasktracker.entities.TaskListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskListRepository extends JpaRepository<TaskListEntity, Long> {
    Optional<TaskListEntity> findByName(String name);
}
