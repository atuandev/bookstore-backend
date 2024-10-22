package com.iuh.service;

import com.iuh.dto.request.BookCreationRequest;
import com.iuh.dto.request.BookUpdateRequest;
import com.iuh.dto.response.BookResponse;
import com.iuh.dto.response.PageResponse;

import java.util.List;

public interface BookService {
    BookResponse save(BookCreationRequest request);

    List<BookResponse> findAll();

    PageResponse<Object> findAllWithSortBy(int pageNo, int pageSize, String sortBy);

    BookResponse findById(String id);

    BookResponse update(String id, BookUpdateRequest request);

    void delete(String id);
}
