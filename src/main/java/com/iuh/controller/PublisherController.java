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
import com.iuh.dto.request.PublisherRequest;
import com.iuh.entity.Publisher;
import com.iuh.service.PublisherService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Publisher Controller")
@RestController
@RequestMapping("/publishers")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
@Slf4j
public class PublisherController {
	PublisherService publisherService;
	
	@GetMapping
	public ApiResponse<List<Publisher>> findAll() {
		return ApiResponse.<List<Publisher>>builder()
				.data(publisherService.findAll())
				.build();
	}
	
	@GetMapping("/{id}")
	public ApiResponse<Publisher> findById(@PathVariable String id) {
        return ApiResponse.<Publisher>builder()
        		.data(publisherService.findById(id))
        		.build();
	}
	
	@PostMapping("/add")
	public ApiResponse<Publisher> addPublisher(@RequestBody PublisherRequest request) {
		return ApiResponse.<Publisher>builder()
				.data(publisherService.save(request))
				.build();
	}
	
	@PutMapping("/{id}")
	public ApiResponse<Publisher> updatePublisher(@PathVariable String id ,@RequestBody PublisherRequest request) {
		log.info("Id: {}",id);
		return ApiResponse.<Publisher>builder()
				.data(publisherService.update(id,request))
				.build();
	}
	@DeleteMapping("/{id}")
	public ApiResponse<Void> deletePublisher(@PathVariable String id) {
		publisherService.delete(id);
		return ApiResponse.<Void>builder().build();
	}
}
