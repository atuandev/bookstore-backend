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
import com.iuh.dto.request.DiscountRequest;
import com.iuh.dto.request.PublisherRequest;
import com.iuh.dto.response.BookResponse;
import com.iuh.entity.Discount;
import com.iuh.entity.Publisher;
import com.iuh.service.DiscountService;
import com.iuh.service.PublisherService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Discount Controller")
@RestController
@RequestMapping("/discounts")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
@Slf4j
public class DiscountController {
	DiscountService discountService;
	
	@GetMapping
	public ApiResponse<List<Discount>> findAll() {
		return ApiResponse.<List<Discount>>builder()
				.data(discountService.findAll())
				.build();
	}
	
//    @Operation(summary = "Get all books")
//    @GetMapping
//    ApiResponse<List<BookResponse>> getAllBooks() {
//        return ApiResponse.<List<BookResponse>>builder()
//                .data(bookService.findAll())
//                .build();
//    }
	
	@GetMapping("/{id}")
	public ApiResponse<Discount> findById(@PathVariable String id) {
        return ApiResponse.<Discount>builder()
        		.data(discountService.findById(id))
        		.build();
	}
	
	@PostMapping("/add")
	public ApiResponse<Discount> addPublisher(@RequestBody DiscountRequest request) {
		return ApiResponse.<Discount>builder()
				.data(discountService.save(request))
				.build();	
	}
	
	@PutMapping("/{id}")
	public ApiResponse<Discount>  updatePublisher(@PathVariable String id ,@RequestBody DiscountRequest request) {
		return  ApiResponse.<Discount>builder()
				.data(discountService.update(id,request))
				.build();
	}
	@DeleteMapping("/{id}")
	public ApiResponse<Void> deletePublisher(@PathVariable String id) {
		discountService.delete(id);
		return ApiResponse.<Void>builder().build();
	}
}
