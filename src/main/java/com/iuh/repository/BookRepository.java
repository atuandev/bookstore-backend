package com.iuh.repository;

import com.iuh.dto.response.BookResponse;
import com.iuh.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {
    Optional<List<BookResponse>> findAllByIsFeaturedTrue();

    Optional<List<BookResponse>> findAllByIsNewTrue();

    Optional<List<BookResponse>> findAllByStatusTrue();

    Optional<BookResponse> findBySlugAndStatusTrue(String slug);

    @Query("SELECT b FROM Book b WHERE b.category.slug = :slug AND b.status = true")
    Optional<List<BookResponse>> findAllByCategorySlugAndStatusTrue(String slug);

    @Query("SELECT b FROM Book b WHERE b.publisher.slug = :slug AND b.status = true")
    Optional<List<BookResponse>> findAllByPublisherSlugAndStatusTrue(String slug);

}
