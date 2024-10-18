package com.iuh.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class UserResponse {
    String id;
    String username;
    String name;
    String email;
    String avatar;
    Boolean status;
    Set<RoleResponse> roles;
    Set<UserAddressResponse> addresses;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
