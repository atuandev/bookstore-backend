package com.iuh.service;

import com.iuh.dto.request.PermissionRequest;
import com.iuh.dto.response.PermissionResponse;

import java.util.List;

public interface PermissionService {
    PermissionResponse save(PermissionRequest request);

    List<PermissionResponse> findAll();

    void delete(String permission);
}
