package com.iuh.service;

import com.iuh.dto.request.CategoryRequest;
import com.iuh.entity.Category;

import java.util.List;

public interface CategoryService {
    Category save(CategoryRequest request);

    List<Category> findAll();

    Category findById(String id);

    Category update(String id, CategoryRequest request);

    void delete(String id);
}
