package com.iuh.service;

import com.iuh.dto.request.UserAddressRequest;
import com.iuh.dto.response.UserAddressResponse;

import java.util.List;

public interface UserAddressService {
    public UserAddressResponse save(UserAddressRequest request);

    public List<UserAddressResponse> findAll();

    public List<UserAddressResponse> findAllByUserId(String userId);

    public UserAddressResponse findById(String id);

    public UserAddressResponse update(String id, UserAddressRequest request);

    public void delete(String id);
}
