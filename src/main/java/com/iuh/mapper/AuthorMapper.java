package com.iuh.mapper;

import com.iuh.dto.request.AuthorRequest;
import com.iuh.dto.response.AuthorResponse;
import com.iuh.entity.Author;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    Author toEntity(AuthorRequest request);

    AuthorResponse toResponse(Author author);

    void toUpdateEntity(@MappingTarget Author category, AuthorRequest request);
}
