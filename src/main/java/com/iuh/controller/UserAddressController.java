package com.iuh.controller;

import com.iuh.dto.ApiResponse;
import com.iuh.dto.request.UserAddressRequest;
import com.iuh.dto.response.UserAddressResponse;
import com.iuh.service.UserAddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "User Address Controller")
@RestController
@RequestMapping("/user-addresses")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class UserAddressController {
    UserAddressService userAddressService;

    @Operation(summary = "Create user address")
    @PostMapping
    ApiResponse<UserAddressResponse> createUserAddress(@RequestBody @Valid UserAddressRequest request) {
        return ApiResponse.<UserAddressResponse>builder()
                .data(userAddressService.save(request))
                .build();
    }

    @Operation(summary = "Get all user addresses")
    @GetMapping
    ApiResponse<List<UserAddressResponse>> getAllUserAddresses() {
        return ApiResponse.<List<UserAddressResponse>>builder()
                .data(userAddressService.findAll())
                .build();
    }

    @Operation(summary = "Get all user addresses by user id")
    @GetMapping("/user/{userId}")
    ApiResponse<List<UserAddressResponse>> getAllUserAddressesByUserId(@PathVariable String userId) {
        return ApiResponse.<List<UserAddressResponse>>builder()
                .data(userAddressService.findAllByUserId(userId))
                .build();
    }

    @Operation(summary = "Get user address details")
    @GetMapping("/{userAddressId}")
    ApiResponse<UserAddressResponse> getUserAddressDetails(@PathVariable String userAddressId) {
        return ApiResponse.<UserAddressResponse>builder()
                .data(userAddressService.findById(userAddressId))
                .build();
    }

    @Operation(summary = "Update user address")
    @PutMapping("/{userAddressId}")
    ApiResponse<UserAddressResponse> updateUserAddress(
            @PathVariable String userAddressId, @RequestBody @Valid UserAddressRequest request) {
        return ApiResponse.<UserAddressResponse>builder()
                .data(userAddressService.update(userAddressId, request))
                .build();
    }

    @Operation(summary = "Delete user address")
    @DeleteMapping("/{userAddressId}")
    ApiResponse<Void> deleteUserAddress(@PathVariable String userAddressId) {
        userAddressService.delete(userAddressId);
        return ApiResponse.<Void>builder()
                .build();
    }
}
