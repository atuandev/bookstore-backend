package com.iuh.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.iuh.dto.request.ReviewRequest;
import com.iuh.dto.response.ReviewResponse;
import com.iuh.entity.Review;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
	
	@Mapping(target = "book", ignore = true)
	@Mapping(target = "user", ignore = true)
    Review toEntity(ReviewRequest request);

//	@BeanMapping(ignoreByDefault = true)
	@Mapping(target = "book", ignore = true)
	@Mapping(target = "user", ignore = true)
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
    void toUpdateEntity(@MappingTarget Review review, ReviewRequest request);
	
	ReviewResponse toResponse(Review review);
}
