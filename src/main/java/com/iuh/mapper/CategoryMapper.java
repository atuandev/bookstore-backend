package com.iuh.mapper;

import com.iuh.dto.request.CategoryRequest;
import com.iuh.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toEntity(CategoryRequest request);

    void toUpdateEntity(@MappingTarget Category category, CategoryRequest request);
}
