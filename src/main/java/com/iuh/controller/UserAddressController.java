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
@RequestMapping("/addresses")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class UserAddressController {
    UserAddressService userAddressService;

    @Operation(summary = "Create user address")
    @PostMapping("/add")
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

    @Operation(summary = "Get user address details")
    @GetMapping("/{addressId}")
    ApiResponse<UserAddressResponse> getUserAddressDetails(@PathVariable String addressId) {
        return ApiResponse.<UserAddressResponse>builder()
                .data(userAddressService.findById(addressId))
                .build();
    }

    @Operation(summary = "Update user address")
    @PutMapping("/{addressId}")
    ApiResponse<UserAddressResponse> updateUserAddress(
            @PathVariable String addressId, @RequestBody @Valid UserAddressRequest request) {
        return ApiResponse.<UserAddressResponse>builder()
                .data(userAddressService.update(addressId, request))
                .build();
    }

    @Operation(summary = "Delete user address")
    @DeleteMapping("/{addressId}")
    ApiResponse<Void> deleteUserAddress(@PathVariable String addressId) {
        userAddressService.delete(addressId);
        return ApiResponse.<Void>builder()
                .build();
    }
}
