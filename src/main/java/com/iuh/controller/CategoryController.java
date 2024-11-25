package com.iuh.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iuh.dto.ApiResponse;
import com.iuh.dto.request.CategoryRequest;
import com.iuh.dto.response.CategoryResponse;
import com.iuh.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Tag(name = "Book Controller")
@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class CategoryController {
	CategoryService categoryService;
//	CategoryMapper categoryMapper;

	@Operation(summary = "Create permission")
	@PostMapping("/add")
	ApiResponse<CategoryResponse> create(@RequestBody CategoryRequest request) {
		return ApiResponse.<CategoryResponse>builder().data(categoryService.save(request))
				.build();
	}
	@GetMapping("/name/{categoryName}")
	@Operation(summary = "Get category by name")
	ApiResponse<CategoryResponse> getCategoryByName(@PathVariable String categoryName) {
        return ApiResponse.<CategoryResponse>builder().data(categoryService.findByName(categoryName)).build();
	}

	@GetMapping
	@Operation(summary = "Get all categories")
	ApiResponse<List<CategoryResponse>> getAll() {
		return ApiResponse.<List<CategoryResponse>>builder().data(categoryService.findAll()).build();
	}
	@GetMapping("/{categoryId}")
	@Operation(summary = "Get category by id")
	ApiResponse<CategoryResponse> getCategoryById(@PathVariable String categoryId) {
		return ApiResponse.<CategoryResponse>builder().data(categoryService.findById(categoryId)).build();
	}
	
	@PutMapping("/{categoryId}")
	@Operation(summary = "Update category by id")
	ApiResponse<CategoryResponse> updateCategoryById(@PathVariable String categoryId,
			@RequestBody CategoryRequest request) {
		return ApiResponse.<CategoryResponse>builder().data(categoryService.update(categoryId, request)).build();
	}
	
	@DeleteMapping("/{categoryId}")
	@Operation(summary = "Delete category by id")
	ApiResponse<Void> deleteCategoryById(@PathVariable String categoryId) {
		categoryService.delete(categoryId);
		return ApiResponse.<Void>builder().build();
	}
	
}
