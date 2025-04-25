package com.lucas.tasktracker.services;

import com.lucas.tasktracker.dtos.AddTaskListDTO;
import com.lucas.tasktracker.dtos.TaskListDTO;
import com.lucas.tasktracker.entities.TaskListEntity;
import com.lucas.tasktracker.mappers.TaskListMapper;
import com.lucas.tasktracker.repositories.TaskListRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskListService {


    TaskListRepository taskListRepository;
    TaskListMapper taskListMapper;

    public TaskListService(TaskListRepository taskListRepository, TaskListMapper taskListMapper) {
        this.taskListRepository = taskListRepository;
        this.taskListMapper = taskListMapper;
    }

    // Crear una nueva lista de tareas
    public Optional<TaskListDTO> createTaskList(AddTaskListDTO addTaskListDTO) {

        TaskListEntity taskListEntity = new TaskListEntity();
        taskListEntity.setDescription(addTaskListDTO.getDescription());
        taskListEntity.setName(addTaskListDTO.getName());

        taskListRepository.save(taskListEntity);


        TaskListDTO taskListDTO = taskListMapper.toTaskListDTO(taskListEntity);

        return Optional.of(taskListDTO);
    }

}
