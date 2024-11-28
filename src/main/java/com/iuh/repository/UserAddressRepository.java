package com.iuh.repository;

import com.iuh.entity.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, String> {
    @Modifying
    @Query("DELETE FROM UserAddress ua WHERE ua.id = :id")
    void deleteUserAddressById(String id);

    List<UserAddress> findAllByOrderByCreatedAtDesc();
}
