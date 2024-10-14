package com.iuh.service;

import com.iuh.dto.request.PermissionRequest;
import com.iuh.dto.response.PermissionResponse;

import java.util.List;

public interface PermissionService {
    public PermissionResponse save(PermissionRequest request);
    public List<PermissionResponse> findAll();
    public void delete(String permission);
}
