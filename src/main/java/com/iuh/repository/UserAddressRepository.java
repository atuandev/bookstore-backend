package com.iuh.repository;

import com.iuh.entity.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, String> {
    List<UserAddress> findAllByUserId(String userId);
}
