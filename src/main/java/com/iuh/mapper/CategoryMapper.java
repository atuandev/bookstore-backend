package com.iuh.mapper;

import com.iuh.dto.request.CategoryRequest;
import com.iuh.dto.response.CategoryResponse;
import com.iuh.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toEntity(CategoryRequest request);

    @Mapping(target = "createdAt", source = "category.createdAt")
    @Mapping(target = "updatedAt", source = "category.updatedAt")
    CategoryResponse toResponse(Category category);

    void toUpdateEntity(@MappingTarget Category category, CategoryRequest request);

}
