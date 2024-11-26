package com.iuh.service;

import java.util.List;

import com.iuh.dto.request.CategoryRequest;
import com.iuh.dto.response.CategoryResponse;
import com.iuh.dto.response.PageResponse;

public interface CategoryService {
    CategoryResponse save(CategoryRequest request);

    List<CategoryResponse> findAll();

    CategoryResponse findById(String id);
    
    CategoryResponse findByName(String name);

    CategoryResponse update(String id, CategoryRequest request);

    PageResponse<List<CategoryResponse>> findAllWithSortBy(int pageNo, int pageSize, String sortBy);
    
    void delete(String id);
    
    void deleteByName(String name);
}
