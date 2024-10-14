package com.iuh.controller;

import com.iuh.dto.ApiResponse;
import com.iuh.dto.request.PermissionRequest;
import com.iuh.dto.response.PermissionResponse;
import com.iuh.service.PermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Permission Controller")
@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class PermissionController {
    PermissionService permissionService;

    @Operation(summary = "Create permission")
    @PostMapping
    ApiResponse<PermissionResponse> create(@RequestBody PermissionRequest request) {
        return ApiResponse.<PermissionResponse>builder()
                .data(permissionService.save(request))
                .build();
    }

    @Operation(summary = "Get all permissions")
    @GetMapping
    ApiResponse<List<PermissionResponse>> getAll() {
        return ApiResponse.<List<PermissionResponse>>builder()
                .data(permissionService.findAll())
                .build();
    }

    @Operation(summary = "Get permission by name")
    @DeleteMapping("/{permission}")
    ApiResponse<Void> delete(@PathVariable String permission) {
        permissionService.delete(permission);
        return ApiResponse.<Void>builder().build();
    }
}
