package com.iuh.repository;

import java.util.List;
import java.util.Optional;

import com.iuh.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);

    List<User> findAllByStatusTrue();
}
