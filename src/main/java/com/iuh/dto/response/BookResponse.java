package com.iuh.dto.response;

import com.iuh.entity.Category;
import com.iuh.entity.Discount;
import com.iuh.entity.Publisher;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class BookResponse {
    String id;
    String title;
    String slug;
    String description;
    String size;
    Integer pages;
    Integer weight;
    Integer publishYear;
    Double price;
    Integer stock;
    Integer sold;
    Boolean isNew;
    Boolean isFeatured;
    Category category;
    Publisher publisher;
    Discount discount;
    Set<BookImageResponse> images;
    Set<AuthorResponse> authors;
}
