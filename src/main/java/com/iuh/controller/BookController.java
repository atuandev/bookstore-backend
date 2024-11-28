package com.iuh.controller;

import com.iuh.dto.ApiResponse;
import com.iuh.dto.request.BookCreationRequest;
import com.iuh.dto.request.BookUpdateRequest;
import com.iuh.dto.response.BookResponse;
import com.iuh.dto.response.PageResponse;
import com.iuh.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Book Controller")
@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class BookController {
    BookService bookService;

    @Operation(summary = "Create book")
    @PostMapping("/add")
    ApiResponse<BookResponse> createBook(@RequestBody @Valid BookCreationRequest request) {
        return ApiResponse.<BookResponse>builder()
                .data(bookService.save(request))
                .build();
    }

    @Operation(summary = "Get all books")
    @GetMapping
    ApiResponse<List<BookResponse>> getAllBooks() {
        return ApiResponse.<List<BookResponse>>builder()
                .data(bookService.findAll())
                .build();
    }

    @Operation(summary = "Get all books with pagination and sort by")
    @GetMapping("/list")
    ApiResponse<PageResponse<Object>> getAllBooksWithSortBy(
            @Min(0) @RequestParam(defaultValue = "0", required = false) int pageNo,
            @Min(4) @RequestParam(defaultValue = "12", required = false) int pageSize,
            @RequestParam(required = false) String sortBy
    ) {
        return ApiResponse.<PageResponse<Object>>builder()
                .data(bookService.findAllWithSortBy(pageNo, pageSize, sortBy))
                .build();
    }

    @Operation(summary = "Get book details by id")
    @GetMapping("/{bookId}")
    ApiResponse<BookResponse> getBookDetails(@PathVariable String bookId) {
        return ApiResponse.<BookResponse>builder()
                .data(bookService.findById(bookId))
                .build();
    }
    @Operation(summary = "Get book details by slug")
    @GetMapping("/slug/{slug}")
    ApiResponse<BookResponse> getBookDetailsBySlug(@PathVariable String slug) {
        return ApiResponse.<BookResponse>builder()
                .data(bookService.findBySlug(slug))
                .build();
    }

    @Operation(summary = "Update book")
    @PutMapping("/{bookId}")
    ApiResponse<BookResponse> updateBook(@PathVariable String bookId, @RequestBody @Valid BookUpdateRequest request) {
        return ApiResponse.<BookResponse>builder()
                .data(bookService.update(bookId, request))
                .build();
    }

    @Operation(summary = "Delete book")
    @DeleteMapping("/{bookId}")
    ApiResponse<Void> deleteBook(@PathVariable String bookId) {
        bookService.delete(bookId);
        return ApiResponse.<Void>builder().build();
    }
}
