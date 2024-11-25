package com.iuh.service;

import java.util.List;

import com.iuh.dto.request.ReviewRequest;
import com.iuh.dto.response.ReviewResponse;

public interface ReviewService {
	void delete(String id);
	
	
	List<ReviewResponse> findAllByBookId(String bookId);
	
	List<ReviewResponse> findAllByUserId(String userId);
	
	ReviewResponse save(ReviewRequest review);

	
}
