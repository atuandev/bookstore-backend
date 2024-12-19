package com.iuh.service;

import com.iuh.dto.request.UserCreationRequest;
import com.iuh.dto.request.UserUpdateRequest;
import com.iuh.dto.response.PageResponse;
import com.iuh.dto.response.UserResponse;
import com.iuh.enums.UserStatus;

public interface UserService {
    UserResponse save(UserCreationRequest request);

    UserResponse findById(String id);

    UserResponse getMyInfo();

    UserResponse update(String id, UserUpdateRequest request);

    void delete(String id);

    PageResponse<Object> findAll(int pageNo, int pageSize, String sortBy, String search);

    PageResponse<Object> findAllWithSpecifications(int pageNo, int pageSize, String sortBy, String[] user);

    void updateStatus(String id, UserStatus status);

    void updatePassword(String id, String oldPassword, String newPassword);

    void updateAvatar(String id, String avatar);
}
