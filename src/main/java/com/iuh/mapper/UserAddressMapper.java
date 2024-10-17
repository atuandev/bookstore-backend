package com.iuh.mapper;

import com.iuh.dto.request.UserAddressRequest;
import com.iuh.dto.response.UserAddressResponse;
import com.iuh.entity.UserAddress;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserAddressMapper {
    UserAddress toUserAddress(UserAddressRequest userAddress);

    UserAddressResponse toUserAddressResponse(UserAddress userAddress);

    void updateUserAddress(@MappingTarget UserAddress userAddress, UserAddressRequest userAddressRequest);
}
