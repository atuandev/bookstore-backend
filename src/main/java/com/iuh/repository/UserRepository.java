package com.iuh.repository;

import java.util.Optional;

import com.iuh.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);

    Page<User> findAllByUsernameContainingIgnoreCaseOrNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String username, String name, String email, Pageable pageable);

}
