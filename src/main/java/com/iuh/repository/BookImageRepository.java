package com.iuh.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.iuh.entity.BookImage;

@RepositoryRestResource
public interface BookImageRepository extends JpaRepository<BookImage, String> {
    @Query("SELECT b FROM BookImage b WHERE b.book.id = :bookId")
    Optional<BookImage> findAllByBookId(String bookId);

    void deleteAllByBookId(String bookId);
}
