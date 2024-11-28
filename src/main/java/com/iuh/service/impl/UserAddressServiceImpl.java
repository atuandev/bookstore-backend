package com.iuh.service.impl;

import com.iuh.dto.request.UserAddressRequest;
import com.iuh.dto.response.UserAddressResponse;
import com.iuh.exception.AppException;
import com.iuh.exception.ErrorCode;
import com.iuh.mapper.UserAddressMapper;
import com.iuh.repository.UserAddressRepository;
import com.iuh.repository.UserRepository;
import com.iuh.service.UserAddressService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserAddressServiceImpl implements UserAddressService {
    UserRepository userRepository;
    UserAddressRepository userAddressRepository;
    UserAddressMapper userAddressMapper;

    @Override
    public UserAddressResponse save(UserAddressRequest request) {
        var userAddress = userAddressMapper.toUserAddress(request);
        userAddress.setUser(userRepository.findById(request.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)));
        return userAddressMapper.toUserAddressResponse(userAddressRepository.save(userAddress));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public List<UserAddressResponse> findAll() {
        return userAddressRepository.findAllByOrderByCreatedAtDesc().stream().map(userAddressMapper::toUserAddressResponse).toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public UserAddressResponse findById(String id) {
        var userAddress = userAddressRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_ADDRESS_NOT_FOUND));
        return userAddressMapper.toUserAddressResponse(userAddress);
    }

    @Override
    public UserAddressResponse update(String id, UserAddressRequest request) {
        var userAddress = userAddressRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_ADDRESS_NOT_FOUND));
        userAddressMapper.updateUserAddress(userAddress, request);

        return userAddressMapper.toUserAddressResponse(userAddressRepository.save(userAddress));
    }

    @Override
    @Transactional
    public void delete(String id) {
        userAddressRepository.deleteUserAddressById(id);
    }
}
