package com.iuh.service.impl;

import com.iuh.dto.request.BookCreationRequest;
import com.iuh.dto.request.BookImageRequest;
import com.iuh.dto.request.BookUpdateRequest;
import com.iuh.dto.response.BookResponse;
import com.iuh.dto.response.PageResponse;
import com.iuh.entity.Book;
import com.iuh.entity.BookImage;
import com.iuh.exception.AppException;
import com.iuh.exception.ErrorCode;
import com.iuh.mapper.BookMapper;
import com.iuh.repository.BookRepository;
import com.iuh.repository.CategoryRepository;
import com.iuh.repository.DiscountRepository;
import com.iuh.repository.PublisherRepository;
import com.iuh.service.BookService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class BookServiceImpl implements BookService {
    BookRepository bookRepository;
    CategoryRepository categoryRepository;
    PublisherRepository publisherRepository;
    DiscountRepository discountRepository;
    BookMapper bookMapper;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public BookResponse save(BookCreationRequest request) {
        Book book = bookMapper.toEntity(request);

        book.setCategory(categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND)));

        book.setPublisher(publisherRepository.findById(request.getPublisherId())
                .orElseThrow(() -> new AppException(ErrorCode.PUBLISHER_NOT_FOUND)));

        book.setDiscount(discountRepository.findByCode(request.getDiscountCode())
                .orElse(null));

        for (BookImageRequest bookImageRequest : request.getBookImages()) {
            book.addBookImage(BookImage.builder()
                    .url(bookImageRequest.getUrl())
                    .build());
        }

        try {
            book = bookRepository.save(book);
        } catch (DataIntegrityViolationException e) {
            throw new AppException(ErrorCode.BOOK_EXISTS);
        }

        return bookMapper.toResponse(book);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<BookResponse> findAll() {
        return bookRepository.findAll().stream().map(bookMapper::toResponse).toList();
    }

    @Override
    public PageResponse<Object> findAllWithSortByAndSearch(int pageNo, int pageSize, String sortBy, String search) {
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

        Page<Book> books;

        if (StringUtils.hasLength(search)) {
            books = bookRepository.findAllByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(search, search, pageable);
        } else {
            books = bookRepository.findAll(pageable);
        }

        List<BookResponse> items = books.map(bookMapper::toResponse).getContent();

        return PageResponse.builder()
                .pageNo(pageable.getPageNumber() + 1)
                .pageSize(pageable.getPageSize())
                .totalPages(books.getTotalPages())
                .items(items)
                .build();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public BookResponse findById(String id) {
        return bookRepository.findById(id).map(bookMapper::toResponse)
                .orElseThrow(() -> new AppException(ErrorCode.BOOK_NOT_FOUND));
    }

    @Override
    public BookResponse findBySlug(String slug) {
        return bookRepository.findBySlug(slug).map(bookMapper::toResponse)
                .orElseThrow(() -> new AppException(ErrorCode.BOOK_NOT_FOUND));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public BookResponse update(String id, BookUpdateRequest request) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.BOOK_NOT_FOUND));
        bookMapper.toUpdateEntity(book, request);

        book.setCategory(categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND)));

        book.setPublisher(publisherRepository.findById(request.getPublisherId())
                .orElseThrow(() -> new AppException(ErrorCode.PUBLISHER_NOT_FOUND)));

        book.setDiscount(discountRepository.findByCode(request.getDiscountCode())
                .orElse(null));

        // update book images
        book.getBookImages().clear();
        for (BookImageRequest bookImageRequest : request.getBookImages()) {
            book.addBookImage(BookImage.builder()
                    .url(bookImageRequest.getUrl())
                    .build());
        }

        try {
            book = bookRepository.save(book);
        } catch (DataIntegrityViolationException e) {
            throw new AppException(ErrorCode.BOOK_EXISTS);
        }

        return bookMapper.toResponse(book);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(String id) {
        bookRepository.deleteById(id);
    }

}
