package com.iuh.repository;

import com.iuh.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, String> {
    @Query("SELECT r FROM Review r WHERE r.book.id = :bookId")
    Optional<List<Review>> findAllByBookId(String bookId);

    @Query("SELECT r FROM Review r WHERE r.user.id = :userId")
    Optional<List<Review>> findAllByUserId(String userId);

    Optional<List<Review>> findAllByRating(Integer rating);
}
