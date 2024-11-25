package com.iuh.mapper;

import com.iuh.dto.request.CategoryRequest;
import com.iuh.dto.response.BookResponse;
import com.iuh.dto.response.CategoryResponse;
import com.iuh.entity.Book;
import com.iuh.entity.Category;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toEntity(CategoryRequest request);

    CategoryResponse toResponse(Category category);
    
    void toUpdateEntity(@MappingTarget Category category, CategoryRequest request);
    
    List<CategoryResponse> toResponseList(List<Category> categories);
    
}
