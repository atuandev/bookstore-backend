package com.iuh.service;

import com.iuh.dto.request.UserAddressRequest;
import com.iuh.dto.response.UserAddressResponse;

import java.util.List;

public interface UserAddressService {
    UserAddressResponse save(UserAddressRequest request);

    List<UserAddressResponse> findAll();

    UserAddressResponse findById(String id);

    UserAddressResponse update(String id, UserAddressRequest request);

    void delete(String id);
}
