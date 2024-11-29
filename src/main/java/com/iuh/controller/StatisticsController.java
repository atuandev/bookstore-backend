package com.iuh.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iuh.dto.ApiResponse;
import com.iuh.dto.response.StatisticsResponse;
import com.iuh.service.StatisticsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Tag(name = "Statistics Controller")
@RestController
@RequestMapping("/stats")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class StatisticsController {

    StatisticsService statisticsService;

    @Operation(summary = "Get statistics by year")
    @GetMapping("/year/{year}")
    public ApiResponse<List<StatisticsResponse>> getStatistics(@PathVariable int year) {
        var statistics = statisticsService.getStatisticsByYear(year);
        return ApiResponse.<List<StatisticsResponse>>builder().data(statistics).build();
    }

}