package com.iuh.controller;

import com.iuh.dto.ApiResponse;
import com.iuh.dto.request.UserCreationRequest;
import com.iuh.dto.request.UserUpdateRequest;
import com.iuh.dto.response.PageResponse;
import com.iuh.dto.response.UserResponse;
import com.iuh.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "User Controller")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class UserController {
    UserService userService;

    @Operation(summary = "Create user")
    @PostMapping("/add")
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
        return ApiResponse.<UserResponse>builder()
                .data(userService.save(request))
                .build();
    }

    @Operation(summary = "Get all users")
    @GetMapping
    ApiResponse<List<UserResponse>> getAllUsers() {
        SecurityContextHolder.getContext().getAuthentication();

        return ApiResponse.<List<UserResponse>>builder()
                .data(userService.findAll())
                .build();
    }

    @Operation(summary = "Get all users with pagination and sort by")
    @GetMapping("/list")
    ApiResponse<PageResponse<Object>> getAllUsersWithSortBy(
            @Min(0) @RequestParam(defaultValue = "0", required = false) int pageNo,
            @Min(4) @RequestParam(defaultValue = "12", required = false) int pageSize,
            @RequestParam(required = false) String sortBy
    ) {
        SecurityContextHolder.getContext().getAuthentication();
        return ApiResponse.<PageResponse<Object>>builder()
                .data(userService.findAllWithSortBy(pageNo, pageSize, sortBy))
                .build();
    }

    @Operation(summary = "Get user details")
    @GetMapping("/{userId}")
    ApiResponse<UserResponse> getUser(@PathVariable String userId) {
        return ApiResponse.<UserResponse>builder()
                .data(userService.findById(userId))
                .build();
    }

    @Operation(summary = "Get user info")
    @GetMapping("/me")
    ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .data(userService.getMyInfo())
                .build();
    }

    @Operation(summary = "Update user")
    @PutMapping("/{userId}")
    ApiResponse<UserResponse> updateUser(
            @PathVariable String userId, @RequestBody @Valid UserUpdateRequest request) {
        return ApiResponse.<UserResponse>builder()
                .data(userService.update(userId, request))
                .build();
    }

    @Operation(summary = "Delete user")
    @DeleteMapping("/{userId}")
    ApiResponse<String> deleteUser(@PathVariable String userId) {
        userService.delete(userId);
        return ApiResponse.<String>builder().data("User deleted").build();
    }
}
