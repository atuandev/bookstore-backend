package com.iuh.repository;

import com.iuh.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

    @Query(nativeQuery = true, value = """
            SELECT role.name, role.description FROM role\s
            JOIN user_roles ON role.name = user_roles.roles_name
            JOIN user ON user.id = user_roles.user_id
            WHERE user.id = :userId
            """)
    List<Role> findByUserId(String userId);
}
