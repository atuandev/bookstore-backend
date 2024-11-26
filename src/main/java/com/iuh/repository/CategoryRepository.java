package com.iuh.repository;

import com.iuh.entity.Category;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
	Optional<Category> findByName(String name);
	void deleteByName(String name);
}
