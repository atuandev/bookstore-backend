package com.iuh.repository;

import com.iuh.entity.Book;
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

    @Query("SELECT b FROM Book b WHERE b.category.slug = :slug AND b.status = true")
    Optional<List<Book>> findAllByCategorySlugAndStatusTrue(String slug);

    @Query("SELECT b FROM Book b WHERE b.publisher.slug = :slug AND b.status = true")
    Optional<List<Book>> findAllByPublisherSlugAndStatusTrue(String slug);

}
