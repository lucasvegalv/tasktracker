package com.lucas.tasktracker.controllers;

import com.lucas.tasktracker.dtos.requests.RequestTaskListDTO;
import com.lucas.tasktracker.dtos.responses.ResponseTaskListDTO;
import com.lucas.tasktracker.dtos.responses.ResponseUserDTO;
import com.lucas.tasktracker.services.TaskListService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/api/tasklists")
public class TaskListController {

    TaskListService taskListService;

    public TaskListController(TaskListService taskListService) {
        this.taskListService = taskListService;
    }

//    GET /api/tasklists: Obtener todas las listas de tareas.
    @GetMapping
    public ResponseEntity<List<ResponseTaskListDTO>> getAllTaskLists() {
        List<ResponseTaskListDTO> responseTaskListDTOS = taskListService.getAllTaskLists().orElse(null);

        return ResponseEntity.ok(responseTaskListDTOS);
    }


//    GET /api/tasklists/{taskListId}: Obtener detalles de una lista de tareas específica.
    @GetMapping("/{tasklistId}")
    public ResponseEntity<ResponseTaskListDTO> getTaskListById(@PathVariable Long tasklistId) {
        ResponseTaskListDTO responseTaskListDTO = taskListService.getTaskListById(tasklistId).orElse(null);

        return ResponseEntity.ok(responseTaskListDTO);
    }


//    DELETE /api/tasklists/{taskListId}: Eliminar una lista de tareas.
    @DeleteMapping("/{taskListId}")
    public ResponseEntity<Set<ResponseTaskListDTO>> deleteTaskListById(@PathVariable Long taskListId) {
        Set<ResponseTaskListDTO> responseTaskListDTOS = taskListService.deleteTaskListById(taskListId).orElse(null);

        return ResponseEntity.ok(responseTaskListDTOS);
    }

//    POST /api/tasklists: Crear una lista de tareas sin proyecto asignado
    @PostMapping
    public ResponseEntity<ResponseTaskListDTO> createTaskList(RequestTaskListDTO requestTaskListDTO) {
        ResponseTaskListDTO responseTaskListDTO = taskListService.createTaskList(requestTaskListDTO).orElse(null);

        return ResponseEntity.ok(responseTaskListDTO);
    }


//    PUT /api/tasklists/{taskListId}: Actualizar una lista de tareas.
    @PatchMapping("/{tasklistId}")
    public ResponseEntity<ResponseTaskListDTO> updateTaskList(@PathVariable Long tasklistId, @RequestBody RequestTaskListDTO requestTaskListDTO) {
        ResponseTaskListDTO responseTaskListDTO = taskListService.updateTaskList(tasklistId, requestTaskListDTO).orElse(null);

        return ResponseEntity.ok(responseTaskListDTO);
    }

}
