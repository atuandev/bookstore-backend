package com.iuh.controller;

import com.iuh.dto.ApiResponse;
import com.iuh.dto.request.BookCreationRequest;
import com.iuh.dto.request.BookUpdateRequest;
import com.iuh.dto.request.OrderCreationRequest;
import com.iuh.dto.response.BookResponse;
import com.iuh.dto.response.OrderResponse;
import com.iuh.dto.response.PageResponse;
import com.iuh.service.BookService;
import com.iuh.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Order Controller")
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class OrderController {
    OrderService orderService;

    @Operation(summary = "Create a new Order")
    @PostMapping
	public ApiResponse<OrderResponse> create(@Valid @RequestBody OrderCreationRequest request) {
		return ApiResponse.<OrderResponse>builder()
				.data(orderService.save(request))
				.build();
	}
    
    @Operation(summary = "remove an order")
    @DeleteMapping("/{id}")
	public ApiResponse<Void> delete(@PathVariable String id) {
		orderService.delete(id);
		return ApiResponse.<Void>builder().build();
	}
    
}
