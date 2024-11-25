package com.iuh.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iuh.dto.ApiResponse;
import com.iuh.dto.request.ReviewRequest;
import com.iuh.dto.response.ReviewResponse;
import com.iuh.service.ReviewService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Review Controller")
@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
@Slf4j
public class ReviewController {
	ReviewService reviewService;
	
	@DeleteMapping("/{id}")
	@Operation(summary = "Delete review by id")
	public ApiResponse<Void> deleteReview(@PathVariable String id) {
		reviewService.delete(id);
		return ApiResponse.<Void>builder().build();
	}
	@PostMapping("/add")
	@Operation(summary = "Add review")
	public ApiResponse<ReviewResponse> addReview(@RequestBody ReviewRequest request) {
		return ApiResponse.<ReviewResponse>builder().data(reviewService.save(request)).build();
	}
	
	@PutMapping("/{id}")
	@Operation(summary = "Update review by id")
	public ApiResponse<ReviewResponse> updateReview(@PathVariable String id, @RequestBody ReviewRequest request) {
		return ApiResponse.<ReviewResponse>builder().data(reviewService.update(id, request)).build();
	}
	
	@GetMapping("/book/{id}")
	@Operation(summary = "Find all reviews by book id")
	public ApiResponse<List<ReviewResponse>> findByBookId(@PathVariable String id) {
		return ApiResponse.<List<ReviewResponse>>builder().data(reviewService.findAllByBookId(id)).build();
	}	
	@GetMapping("/user/{id}")
	@Operation(summary = "Find all reviews by user id")
	public ApiResponse<List<ReviewResponse>> findByUserId(@PathVariable String id) {
		return ApiResponse.<List<ReviewResponse>>builder().data(reviewService.findAllByUserId(id)).build();
	}
}
