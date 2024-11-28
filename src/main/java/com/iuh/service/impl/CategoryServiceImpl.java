package com.iuh.service.impl;

import com.iuh.dto.request.CategoryRequest;
import com.iuh.dto.response.CategoryResponse;
import com.iuh.dto.response.PageResponse;
import com.iuh.dto.response.UserResponse;
import com.iuh.entity.Category;
import com.iuh.entity.User;
import com.iuh.exception.AppException;
import com.iuh.exception.ErrorCode;
import com.iuh.mapper.CategoryMapper;
import com.iuh.repository.CategoryRepository;
import com.iuh.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@lombok.extern.slf4j.Slf4j
public class CategoryServiceImpl implements CategoryService {
	CategoryRepository categoryRepository;
	CategoryMapper categoryMapper;

	@Override
	public CategoryResponse save(CategoryRequest request) {
		if (categoryRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.CATEGORY_NAME_EXISTED);
		}
		if (categoryRepository.existsBySlug(request.getSlug())) {
			throw new AppException(ErrorCode.CATEGORY_SLUG_EXISTED);
		}
		return categoryMapper.toResponse(categoryRepository.save(categoryMapper.toEntity(request)));
	}

	@Override
	public List<CategoryResponse> findAll() {

		List<Category> ls = categoryRepository.findAll();
		return categoryMapper.toResponseList(ls);

	}

	@Override
	public CategoryResponse findById(String id) {
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
		return categoryMapper.toResponse(category);
	}

	@Override
	public CategoryResponse update(String id, CategoryRequest request) {
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

		categoryMapper.toUpdateEntity(category, request);
//		log.info("category: {}", request);
		return categoryMapper.toResponse(categoryRepository.save(category));
	}

	@Override
	public void delete(String id) {
		if (!categoryRepository.existsById(id)) {
			throw new AppException(ErrorCode.CATEGORY_NOT_FOUND);
		}
		categoryRepository.deleteById(id);
	}

	@Override
	public CategoryResponse findByName(String name) {
		Category category = categoryRepository.findByName(name)
				.orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
		return categoryMapper.toResponse(category);
	}

	@Override
	public void deleteByName(String name) {
		categoryRepository.findByName(name).orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
		categoryRepository.deleteByName(name);

	}

	@Override
	public PageResponse<List<CategoryResponse>> findAllWithSortBy(int pageNo, int pageSize, String sortBy) {
		// TODO Auto-generated method stub
		int page = pageNo > 0 ? pageNo - 1 : 0;
//
		List<Sort.Order> sorts = new ArrayList<>();
//
		if (StringUtils.hasLength(sortBy)) {
			// Regex to match the pattern of sortBy
			// Example: name:asc
			Pattern pattern = Pattern.compile("(\\w+?)(:)(asc|desc)");
			Matcher matcher = pattern.matcher(sortBy);
			if (matcher.find()) {
				if (matcher.group(3).equalsIgnoreCase("asc")) {
					sorts.add(new Sort.Order(Sort.Direction.ASC, matcher.group(1)));
				} else {
					sorts.add(new Sort.Order(Sort.Direction.DESC, matcher.group(1)));
				}
			}
		}
		Pageable pageable = PageRequest.of(page, pageSize, Sort.by(sorts));
		Page<Category> users = categoryRepository.findAll(pageable);
		List<CategoryResponse> items = users.map(categoryMapper::toResponse).getContent();
		
		return PageResponse.<List<CategoryResponse>>builder().pageNo(pageable.getPageNumber() + 1)
				.pageSize(pageable.getPageSize()).totalPages(users.getTotalPages()).items(items).build();

//		return ;
	}
}
