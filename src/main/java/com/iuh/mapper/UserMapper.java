package com.iuh.mapper;

import java.util.List;

import com.iuh.dto.response.PageResponse;
import com.iuh.dto.response.UserResponse;
import com.iuh.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.iuh.dto.request.UserCreationRequest;
import com.iuh.dto.request.UserUpdateRequest;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);

    List<UserResponse> toUserResponseList(List<User> users);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
