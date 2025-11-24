package com.ktiservice.patrimoine.controller;

import com.ktiservice.patrimoine.dto.users.CreateUserRequest;
import com.ktiservice.patrimoine.dto.users.UserResponse;
import com.ktiservice.patrimoine.models.User;
import com.ktiservice.patrimoine.services.UserApplicationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserApplicationService userApplicationService;

    public UserController( UserApplicationService userApplicationService) {
        this.userApplicationService = userApplicationService;
    }

    // ------------------- CREATE -------------------
    @PostMapping
    public UserResponse create(@RequestBody CreateUserRequest request,
                               @RequestHeader("X-Admin-Email") String adminEmail) {
        return userApplicationService.create(request, adminEmail);
    }

    // ------------------- GET BY ID -------------------
    @GetMapping("/{id}")
    public UserResponse getById(@PathVariable UUID id) {
        return userApplicationService.getById(id);
    }

    // ------------------- GET ALL -------------------
    @GetMapping
    public List<UserResponse> getAll() {
        return userApplicationService.getAll();
    }

    // ------------------- UPDATE -------------------
    @PutMapping("/{id}")
    public UserResponse update(@PathVariable UUID id, @RequestBody User user) {

        return userApplicationService.update(
                id,
                user.getFirstName(),
                user.getLastName(),
                user.getPhoneNumber(),
                user.getUpdatedBy()  // ou l’email de l’utilisateur connecté
        );
    }

    // ------------------- DELETE -------------------
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id,
                       @RequestHeader("X-Admin-Email") String adminEmail) {
        userApplicationService.delete(id, adminEmail);
    }

    // ------------------- FIND BY EMAIL -------------------
    @GetMapping("/email/{email}")
    public UserResponse getByEmail(@PathVariable String email) {
        return userApplicationService.getUserByEmail(email);
    }
}
