package com.lucas.tasktracker.controllers;

import com.lucas.tasktracker.dtos.AddTaskListDTO;
import com.lucas.tasktracker.dtos.TaskListDTO;
import com.lucas.tasktracker.entities.TaskListEntity;
import com.lucas.tasktracker.services.TaskListService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/tasklists")
public class TaskListController {

    TaskListService taskListService;

    public TaskListController(TaskListService taskListService) {
        this.taskListService = taskListService;
    }

    @PostMapping
    public ResponseEntity<TaskListDTO> createTaskList(AddTaskListDTO addTaskListDTO) {
        TaskListDTO taskListDTO = taskListService.createTaskList(addTaskListDTO).orElse(null);

        return ResponseEntity.ok(taskListDTO);
    }

}
