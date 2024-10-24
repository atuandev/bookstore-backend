package com.iuh.mapper;

import com.iuh.dto.request.BookCreationRequest;
import com.iuh.dto.request.BookUpdateRequest;
import com.iuh.dto.response.BookResponse;
import com.iuh.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BookMapper {
    Book toEntity(BookCreationRequest request);

    BookResponse toResponse(Book book);

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "publisher", ignore = true)
    @Mapping(target = "discount", ignore = true)
    @Mapping(target = "authors", ignore = true)
    @Mapping(target = "bookImages", ignore = true)
    void toUpdateEntity(@MappingTarget Book category, BookUpdateRequest request);
}
