package com.iuh.service;

import com.iuh.dto.request.RoleRequest;
import com.iuh.dto.response.RoleResponse;

import java.util.List;

public interface RoleService {
    RoleResponse save(RoleRequest request);

    List<RoleResponse> findAll();

    void delete(String role);
}
