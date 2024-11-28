package com.iuh.service.impl;

import com.iuh.dto.request.CategoryRequest;
import com.iuh.dto.response.CategoryResponse;
import com.iuh.entity.Category;
import com.iuh.exception.AppException;
import com.iuh.exception.ErrorCode;
import com.iuh.mapper.CategoryMapper;
import com.iuh.repository.CategoryRepository;
import com.iuh.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class CategoryServiceImpl implements CategoryService {
    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;

    @Override
    public CategoryResponse save(CategoryRequest request) {
        return categoryMapper.toResponse(categoryRepository.save(categoryMapper.toEntity(request)));
    }

    @Override
    public List<CategoryResponse> findAll() {
        return categoryMapper.toResponseList(categoryRepository.findAll());
    }

    @Override
    public CategoryResponse findById(String id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        return categoryMapper.toResponse(category);
    }

    @Override
    public CategoryResponse update(String id, CategoryRequest request) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        categoryMapper.toUpdateEntity(category, request);
        return categoryMapper.toResponse(categoryRepository.save(category));
    }

    @Override
    public void delete(String id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public CategoryResponse findBySlug(String slug) {
        Category category = categoryRepository.findBySlug(slug)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        return categoryMapper.toResponse(category);
    }
}
