package com.iuh.controller;

import com.iuh.dto.response.BookStatisticsResponse;
import jakarta.validation.constraints.Min;
import org.springframework.web.bind.annotation.*;

import com.iuh.dto.ApiResponse;
import com.iuh.dto.response.StatisticsResponse;
import com.iuh.service.StatisticsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Collections;
import java.util.List;

@Tag(name = "Statistics Controller")
@RestController
@RequestMapping("/stats")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class StatisticsController {
    StatisticsService statisticsService;

    @Operation(summary = "Get all-time statistics")
    @GetMapping("all")
    public ApiResponse<List<StatisticsResponse>> getAllStatistics() {
        var statistics = statisticsService.getAllStatistics();
        return ApiResponse.<List<StatisticsResponse>>builder().data(statistics).build();
    }

    @Operation(summary = "Get statistics by year")
    @GetMapping("/year/{year}")
    public ApiResponse<List<StatisticsResponse>> getStatistics(@PathVariable int year) {
        var statistics = statisticsService.getStatisticsByYear(year);
        return ApiResponse.<List<StatisticsResponse>>builder().data(statistics).build();
    }

    @Operation(summary = "Get most selling books")
    @GetMapping("/books/all")
    public ApiResponse<List<BookStatisticsResponse>> getMostSellingBooks(
            @Min(1) @RequestParam(defaultValue = "10", required = false) int limit
    ) {
        var statistics = statisticsService.getMostSellingBooks(limit);
        return ApiResponse.<List<BookStatisticsResponse>>builder().data(statistics).build();
    }
    @Operation(summary = "Get most selling books by month (in current year)")
    @GetMapping("/books/month/{month}")
    public ApiResponse<List<Object>> getMostSellingBooksByMonth(
            @PathVariable int month
    ) {
        var statistics = statisticsService.getMostSellingBooksByMonth(month);
        return ApiResponse.<List<Object>>builder().data(Collections.singletonList(statistics)).build();
    }


}