package com.iuh.service;

import com.iuh.dto.request.CategoryRequest;
import com.iuh.dto.response.CategoryResponse;
import com.iuh.entity.Category;

import java.util.List;

public interface CategoryService {
    CategoryResponse save(CategoryRequest request);

    List<CategoryResponse> findAll();

    CategoryResponse findById(String id);
    
    CategoryResponse findByName(String name);

    CategoryResponse update(String id, CategoryRequest request);

    void delete(String id);
    
    void deleteByName(String name);
}
