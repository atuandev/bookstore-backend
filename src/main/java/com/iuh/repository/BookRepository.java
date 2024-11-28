package com.iuh.repository;

import com.iuh.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {
    Optional<List<Book>> findAllByIsFeaturedTrue();

    Optional<List<Book>> findAllByIsNewTrue();

    Optional<List<Book>> findAllByStatusTrue();

    Optional<Book> findBySlug(String slug);

    Page<Book> findAllByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(String title, String author, Pageable pageable);

    @Query("SELECT b FROM Book b WHERE b.category.slug = :categorySlug AND (b.title LIKE %:title% OR b.author LIKE %:author%)")
    Page<Book> findWithFilterAndSearch(String categorySlug, String title, String author, Pageable pageable);
}
