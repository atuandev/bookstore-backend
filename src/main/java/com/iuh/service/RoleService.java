package com.iuh.service;

import com.iuh.dto.request.RoleRequest;
import com.iuh.dto.response.RoleResponse;

import java.util.List;

public interface RoleService {
    public RoleResponse save(RoleRequest request);
    public List<RoleResponse> findAll();
    public void delete(String role);
}
