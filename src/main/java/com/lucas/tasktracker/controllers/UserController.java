package com.lucas.tasktracker.controllers;

import com.lucas.tasktracker.dtos.UserDTO;
import com.lucas.tasktracker.services.UserService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // POST /api/users: Crear un nuevo usuario.
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        UserDTO newUser = userService.crearUsuario(userDTO);
        return ResponseEntity.ok(newUser);
    }

    // GET /api/users: Obtener lista paginada y ordenada de usuarios.
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getUsers();

        return ResponseEntity.ok(users);
    }


    // GET /api/users/{userId}: Obtener detalles de un usuario específico.
    @GetMapping("/{user_id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long user_id) {
     Optional<UserDTO> userDTO = userService.getUserById(user_id);

     return userDTO
             .map(ResponseEntity::ok)
             .orElse(ResponseEntity.notFound().build());
    }


    // PUT /api/users/{userId}: Actualizar un usuario existente.
    @PatchMapping("/{user_id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long user_id, @RequestBody UserDTO userDTO) {
        Optional<UserDTO> updatedUser = userService.updateUser(user_id, userDTO);

        return updatedUser.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }



    // DELETE /api/users/{userId}: Eliminar un usuario.
    @DeleteMapping("/{user_id}")
    public ResponseEntity<List<UserDTO>> deleteUser(@PathVariable Long user_id) {
        List<UserDTO> lastUsers = userService.deleteUser(user_id);

        return ResponseEntity.ok(lastUsers);
    }

    // GET /api/users/{userId}/projects: Listar los proyectos a los que pertenece un usuario.

}
