package com.iuh.service;

import com.iuh.dto.request.UserCreationRequest;
import com.iuh.dto.request.UserUpdateRequest;
import com.iuh.dto.response.PageResponse;
import com.iuh.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse save(UserCreationRequest request);

    List<UserResponse> findAll();

    UserResponse findById(String id);

    UserResponse getMyInfo();

    UserResponse update(String id, UserUpdateRequest request);

    void delete(String id);

    PageResponse<Object> findAllWithSortBy(int pageNo, int pageSize, String sortBy, String search);
}
