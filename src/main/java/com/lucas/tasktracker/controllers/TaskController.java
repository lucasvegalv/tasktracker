package com.lucas.tasktracker.controllers;

import com.lucas.tasktracker.dtos.requests.RequestTaskDTO;
import com.lucas.tasktracker.dtos.responses.ResponseTaskDTO;
import com.lucas.tasktracker.services.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<ResponseTaskDTO> getTaskById(@PathVariable Long taskId) {
        ResponseTaskDTO responseTaskDTO = taskService.getTaskById(taskId);

        return ResponseEntity.ok(responseTaskDTO);
    }

    @PostMapping
    public ResponseEntity<ResponseTaskDTO> createTask(@RequestBody @Valid RequestTaskDTO requestTaskDTO) {
        ResponseTaskDTO responseTaskDTO = taskService.createTask(requestTaskDTO);

        return ResponseEntity.ok(responseTaskDTO);
    }


    @PatchMapping("/{taskId}")
    public ResponseEntity<ResponseTaskDTO> updateTask(Long taskId, @RequestBody @Valid RequestTaskDTO requestTaskDTO) {
        ResponseTaskDTO responseTaskDTO = taskService.updateTask(taskId, requestTaskDTO);

        return ResponseEntity.ok(responseTaskDTO);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Set<ResponseTaskDTO>> deleteTask(@PathVariable Long taskId) {
        Set<ResponseTaskDTO> responseTaskDTOS = taskService.deleteTask(taskId);

        return ResponseEntity.ok(responseTaskDTOS);
    }

}
