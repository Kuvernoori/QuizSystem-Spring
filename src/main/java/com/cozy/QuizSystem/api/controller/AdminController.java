package com.cozy.QuizSystem.api.controller;
import com.cozy.QuizSystem.application.dto.*;
import com.cozy.QuizSystem.application.service.AdminService;
import com.cozy.QuizSystem.application.service.CourseService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/admin/users")
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasRole('ADMIN')")

public class AdminController {
    private final AdminService adminService;
    private final CourseService courseService;

    public AdminController(AdminService adminService, CourseService courseService) {
        this.adminService = adminService;
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.getUserById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponse> updateProfile(
            @PathVariable Long id,
            @Valid @RequestBody AdminUpdateProfileRequest request) {
        return ResponseEntity.ok(adminService.updateProfile(id, request));
    }
    @PatchMapping("/{id}/role")
    public ResponseEntity<Void> changeRole(
            @PathVariable Long id,
            @RequestBody RoleRequest request) {
        adminService.changeRole(id, request.getRole());
        return ResponseEntity.ok().build();
    }
    @PatchMapping("/{id}/password")
    public ResponseEntity<Void> updatePassword(
            @PathVariable Long id,
            @Valid @RequestBody AdminUpdatePasswordRequest request) {
                adminService.updatePassword(id, request);
                return ResponseEntity.ok().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        adminService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/categories")
    public ResponseEntity<CategoryResponse> createCategory(
            @Valid @RequestBody CategoryRequest request) {
        return ResponseEntity.status(201).body(courseService.createCategory(request));
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        courseService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }


}
