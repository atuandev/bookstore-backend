package com.iuh.service.impl;

import com.iuh.dto.request.CategoryRequest;
import com.iuh.dto.response.CategoryResponse;
import com.iuh.dto.response.PageResponse;
import com.iuh.entity.Category;
import com.iuh.exception.AppException;
import com.iuh.exception.ErrorCode;
import com.iuh.mapper.CategoryMapper;
import com.iuh.repository.CategoryRepository;
import com.iuh.service.CategoryService;
import com.iuh.util.PageResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class CategoryServiceImpl implements CategoryService {
    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public CategoryResponse save(CategoryRequest request) {
        return categoryMapper.toResponse(categoryRepository.save(categoryMapper.toEntity(request)));
    }

    @Override
    public PageResponse<Object> findAll(int pageNo, int pageSize, String sortBy, String search) {
        int page = pageNo > 0 ? pageNo - 1 : 0;

        List<Sort.Order> sorts = PageResponseUtil.getSorts(sortBy);

        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(sorts));

        Page<Category> categories = categoryRepository.findAllByNameContainingIgnoreCase(search, pageable);

        List<CategoryResponse> items = categories.map(categoryMapper::toResponse).getContent();

        return PageResponse.builder()
                .pageNo(pageable.getPageNumber() + 1)
                .pageSize(pageable.getPageSize())
                .totalPages(categories.getTotalPages())
                .totalElements(categories.getTotalElements())
                .items(items)
                .build();
    }

    @Override
    public CategoryResponse findBySlug(String slug) {
        Category category = categoryRepository.findBySlug(slug)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        return categoryMapper.toResponse(category);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public CategoryResponse findById(String id) {
        Category category = getCategoryById(id);
        return categoryMapper.toResponse(category);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public CategoryResponse update(String id, CategoryRequest request) {
        Category category = getCategoryById(id);
        categoryMapper.toUpdateEntity(category, request);
        return categoryMapper.toResponse(categoryRepository.save(category));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(String id) {
        Category category = getCategoryById(id);
        categoryRepository.deleteById(category.getId());
    }

    private Category getCategoryById(String id) {
        return categoryRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
    }
}
