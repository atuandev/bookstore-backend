package com.iuh.service.impl;

import com.iuh.dto.request.BookCreationRequest;
import com.iuh.dto.request.BookUpdateRequest;
import com.iuh.dto.response.BookResponse;
import com.iuh.dto.response.PageResponse;
import com.iuh.entity.Author;
import com.iuh.entity.Book;
import com.iuh.entity.BookImage;
import com.iuh.exception.AppException;
import com.iuh.exception.ErrorCode;
import com.iuh.mapper.AuthorMapper;
import com.iuh.mapper.BookImageMapper;
import com.iuh.mapper.BookMapper;
import com.iuh.repository.*;
import com.iuh.service.BookService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class BookServiceImpl implements BookService {
    BookRepository bookRepository;
    BookImageRepository bookImageRepository;
    CategoryRepository categoryRepository;
    PublisherRepository publisherRepository;
    DiscountRepository discountRepository;
    AuthorRepository authorRepository;
    BookMapper bookMapper;
    BookImageMapper bookImageMapper;
    AuthorMapper authorMapper;

    @Override
    public BookResponse save(BookCreationRequest request) {
        Book book = bookMapper.toEntity(request);

        book.setCategory(categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND)));

        book.setPublisher(publisherRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.PUBLISHER_NOT_FOUND)));

        book.setDiscount(discountRepository.findByCode(request.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.DISCOUNT_NOT_FOUND)));

        Set<Author> authors = request.getAuthor().stream()
                .map(authorMapper::toEntity)
                .map(authorRepository::save)
                .collect(Collectors.toSet());
        book.setAuthors(authors);

        Set<BookImage> bookImages = request.getImages().stream()
                .map(bookImageMapper::toEntity)
                .map(bookImageRepository::save)
                .collect(Collectors.toSet());
        book.setBookImages(bookImages);

        return bookMapper.toResponse(bookRepository.save(book));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<BookResponse> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PageResponse<Object> findAllWithSortBy(int pageNo, int pageSize, String sortBy) {
        int page = pageNo > 0 ? pageNo - 1 : 0;

        List<Sort.Order> sorts = new ArrayList<>();

        if (StringUtils.hasLength(sortBy)) {
            // Regex to match the pattern of sortBy
            // Example: name:asc
            Pattern pattern = Pattern.compile("(\\w+?)(:)(asc|desc)");
            Matcher matcher = pattern.matcher(sortBy);
            if (matcher.find()) {
                if (matcher.group(3).equalsIgnoreCase("asc")) {
                    sorts.add(new Sort.Order(Sort.Direction.ASC, matcher.group(1)));
                } else {
                    sorts.add(new Sort.Order(Sort.Direction.DESC, matcher.group(1)));
                }
            }
        }

        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(sorts));

        Page<Book> books = bookRepository.findAll(pageable);

        List<BookResponse> items = books.map(bookMapper::toResponse).getContent();

        return PageResponse.builder()
                .pageNo(pageable.getPageNumber() + 1)
                .pageSize(pageable.getPageSize())
                .totalPages(books.getTotalPages())
                .items(items)
                .build();
    }

    @Override
    public BookResponse findById(String id) {
        return bookRepository.findById(id).map(bookMapper::toResponse)
                .orElseThrow(() -> new AppException(ErrorCode.BOOK_NOT_FOUND));
    }

    @Override
    public BookResponse update(String id, BookUpdateRequest request) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.BOOK_NOT_FOUND));

        bookMapper.toUpdateEntity(book, request);

        book.setCategory(categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND)));

        book.setPublisher(publisherRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.PUBLISHER_NOT_FOUND)));

        book.setDiscount(discountRepository.findByCode(request.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.DISCOUNT_NOT_FOUND)));

        Set<Author> authors = request.getAuthor().stream()
                .map(authorMapper::toEntity)
                .map(authorRepository::save)
                .collect(Collectors.toSet());
        book.setAuthors(authors);

        Set<BookImage> bookImages = request.getImages().stream()
                .map(bookImageMapper::toEntity)
                .map(bookImageRepository::save)
                .collect(Collectors.toSet());
        book.setBookImages(bookImages);

        return bookMapper.toResponse(bookRepository.save(book));
    }

    @Override
    public void delete(String id) {
        bookRepository.deleteById(id);
    }
}
