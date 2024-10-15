package com.iuh.service;

import com.iuh.dto.request.UserCreationRequest;
import com.iuh.dto.request.UserUpdateRequest;
import com.iuh.dto.response.PageResponse;
import com.iuh.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    public UserResponse save(UserCreationRequest request);

    public List<UserResponse> findAll();

    public UserResponse findById(String id);

    public UserResponse getMyInfo();

    public UserResponse update(String id, UserUpdateRequest request);

    public void delete(String id);

    public void deleteAll();

    public PageResponse<Object> findAllUsersWithSortBy(int pageNo, int pageSize, String sortBy);
}
