package com.lucas.tasktracker.controllers;


import com.lucas.tasktracker.dtos.requests.RequestUserDTO;
import com.lucas.tasktracker.dtos.responses.ResponseProjectDTO;
import com.lucas.tasktracker.dtos.responses.ResponseUserDTO;
import com.lucas.tasktracker.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Crear un nuevo usuario.
    @PostMapping
    public ResponseEntity<ResponseUserDTO> createUser(@RequestBody RequestUserDTO requestUserDTO) {
        ResponseUserDTO newUser = userService.createUser(requestUserDTO);
        return ResponseEntity.ok(newUser);
    }

    // Obtener lista paginada y ordenada de usuarios.
    @GetMapping
    public ResponseEntity<List<ResponseUserDTO>> getAllUsers() {
        List<ResponseUserDTO> users = userService.getUsers();

        return ResponseEntity.ok(users);
    }


    // Obtener detalles de un usuario específico.
    @GetMapping("/{user_id}")
    public ResponseEntity<ResponseUserDTO> getUser(@PathVariable Long user_id) {
     Optional<ResponseUserDTO> userDTO = userService.getUserById(user_id);

     return userDTO
             .map(ResponseEntity::ok)
             .orElse(ResponseEntity.notFound().build());
    }

    // Actualizar un usuario existente.
    @PatchMapping("/{user_id}")
    public ResponseEntity<ResponseUserDTO> updateUser(@PathVariable Long user_id, @RequestBody RequestUserDTO requestUserDTO) {
        ResponseUserDTO updatedUser = userService.updateUser(user_id, requestUserDTO);

        return updatedUser.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Eliminar un usuario.
    @DeleteMapping("/{user_id}")
    public ResponseEntity<List<ResponseUserDTO>> deleteUser(@PathVariable Long user_id) {
        List<ResponseUserDTO> lastUsers = userService.deleteUser(user_id);

        return ResponseEntity.ok(lastUsers);
    }

    // Listar los proyectos a los que pertenece un usuario.
    @GetMapping("/{user_id}/projects")
    public ResponseEntity<Set<ResponseProjectDTO>> getUserProjects(@PathVariable Long user_id) {
        Set<ResponseProjectDTO> userProjectsDTO = userService.getUserProjects(user_id);

        return userProjectsDTO.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
