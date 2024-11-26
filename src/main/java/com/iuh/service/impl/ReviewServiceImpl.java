package com.iuh.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.iuh.dto.request.ReviewRequest;
import com.iuh.dto.response.ReviewResponse;
import com.iuh.entity.Book;
import com.iuh.entity.Review;
import com.iuh.entity.User;
import com.iuh.exception.AppException;
import com.iuh.exception.ErrorCode;
import com.iuh.mapper.ReviewMapper;
import com.iuh.repository.BookRepository;
import com.iuh.repository.ReviewRepository;
import com.iuh.repository.UserRepository;
import com.iuh.service.ReviewService;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class ReviewServiceImpl implements ReviewService {
	ReviewRepository reviewRepository;
	ReviewMapper reviewMapper;
	UserRepository userRepository;
	BookRepository bookRepository;

	@Override
	public void delete(String id) {
		reviewRepository.deleteById(id);

	}

	@Override
	public List<ReviewResponse> findAllByBookId(String bookId) {
		Optional<List<Review>> allByBookId = reviewRepository.findAllByBookId(bookId);
		List<ReviewResponse> ls = new ArrayList<>();
		if (allByBookId.isPresent()) {
			for (Review review : allByBookId.get()) {
				ReviewResponse response = reviewMapper.toResponse(review);
				response.setBookTitle(review.getBook().getTitle());
				response.setUserName(review.getUser().getName());
				ls.add(response);
			}
		}
		return ls;
	}

	@Override
	public List<ReviewResponse> findAllByUserId(String userId) {
		Optional<List<Review>> allByBookId = reviewRepository.findAllByUserId(userId);
		List<ReviewResponse> ls = new ArrayList<>();
		if (allByBookId.isPresent()) {
			for (Review review : allByBookId.get()) {
				ReviewResponse response = reviewMapper.toResponse(review);
				response.setBookTitle(review.getBook().getTitle());
				response.setUserName(review.getUser().getName());
				ls.add(response);
			}
		}
		return ls;
	}

	@Override
	public ReviewResponse save(ReviewRequest review) {
		Review save = reviewMapper.toEntity(review);
		Book book = bookRepository.findById(review.getBookId())
				.orElseThrow(() -> new AppException(ErrorCode.BOOK_NOT_FOUND));
		save.setBook(book);
		User user = userRepository.findById(review.getUserId())
				.orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));;
		save.setUser(user);
		ReviewResponse response = reviewMapper.toResponse(reviewRepository.save(save));
		response.setBookTitle(book.getTitle());
		response.setUserName(user.getName());
		return response;
	}

	@Override
	public ReviewResponse update(String id, ReviewRequest review) {
		Review review2 = reviewRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.REVIEW_NOT_FOUND));
		reviewMapper.toUpdateEntity(review2, review);
		ReviewResponse response = reviewMapper.toResponse(reviewRepository.save(review2));
		response.setBookTitle(review2.getBook().getTitle());
		response.setUserName(review2.getUser().getName());
		return response;
	}

}
