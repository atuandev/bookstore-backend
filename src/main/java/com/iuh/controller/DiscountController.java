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

import com.iuh.dto.request.DiscountRequest;
import com.iuh.dto.request.PublisherRequest;
import com.iuh.entity.Discount;
import com.iuh.entity.Publisher;
import com.iuh.service.DiscountService;
import com.iuh.service.PublisherService;

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
	public List<Discount> findAll() {
		return discountService.findAll();
	}
	
	@GetMapping("/{id}")
	public Discount findById(@PathVariable String id) {
        return discountService.findById(id);
	}
	
	@PostMapping("/add")
	public Discount addPublisher(@RequestBody DiscountRequest request) {
		return discountService.save(request);
	}
	
	@PutMapping("/{id}")
	public Discount updatePublisher(@PathVariable String id ,@RequestBody DiscountRequest request) {
		log.info("Id: {}",id);
		return discountService.update(id,request);
	}
	@DeleteMapping("/{id}")
	public void deletePublisher(@PathVariable String id) {
		discountService.delete(id);
	}
}
