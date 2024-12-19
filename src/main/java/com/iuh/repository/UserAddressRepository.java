package com.iuh.repository;

import com.iuh.entity.UserAddress;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, String> {
    @Modifying
    @Query("DELETE FROM UserAddress ua WHERE ua.id = :id")
    void deleteUserAddressById(String id);

    @Query("""
                 SELECT ua FROM UserAddress ua\s
                 WHERE LOWER(ua.address) LIKE %:address%\s
                 OR LOWER(ua.receiverName) LIKE %:receiverName%\s
                 OR LOWER(ua.receiverPhone) LIKE %:receiverPhone%
            """)
    Page<UserAddress> findAllWithSearch(String address, String receiverName, String receiverPhone, Pageable pageable);
}
