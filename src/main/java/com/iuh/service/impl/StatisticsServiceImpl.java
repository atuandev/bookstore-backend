package com.iuh.service.impl;

import java.time.Year;
import java.util.List;
import java.util.stream.Collectors;

import com.iuh.dto.response.BookStatisticsResponse;
import com.iuh.entity.BookStatistics;
import com.iuh.entity.Statistics;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.iuh.dto.response.StatisticsResponse;
import com.iuh.repository.StatisticsRepository;
import com.iuh.service.StatisticsService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class StatisticsServiceImpl implements StatisticsService {

    StatisticsRepository statisticsRepository;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<StatisticsResponse> getStatisticsByYear(int year) {
        List<Statistics> listStat = statisticsRepository.getMonthlyReportByYear(year);
        return listStat.stream()
                .map(stat -> new StatisticsResponse(
                        stat.getMonth(),
                        stat.getTotalOrders(),
                        stat.getTotalImportCost(),
                        stat.getTotalSoldAmount(),
                        stat.getTotalProfit()))
                .collect(Collectors.toList());
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<StatisticsResponse> getAllStatistics() {
        List<Statistics> listStat = statisticsRepository.getAllTimeStats();
        return listStat.stream()
                .map(stat -> new StatisticsResponse(
                        stat.getMonth(),
                        stat.getTotalOrders(),
                        stat.getTotalImportCost(),
                        stat.getTotalSoldAmount(),
                        stat.getTotalProfit()))
                .collect(Collectors.toList());
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<BookStatisticsResponse> getMostSellingBooks(int limit) {
        List<BookStatistics> listStat = statisticsRepository.getMostSellingBooks(limit);
        return listStat.stream()
                .map(stat -> new BookStatisticsResponse(
                        stat.getBookId(),
                        stat.getBookTitle(),
                        stat.getTotalSold()))
                .collect(Collectors.toList());
    }

    @Override
    public List<BookStatisticsResponse> getMostSellingBooksByMonth(int month) {
        int year = Year.now().getValue();
        List<BookStatistics> listStat = statisticsRepository.getMostSellingBooksByMonth(month, year);
        return listStat.stream()
                .map(stat -> new BookStatisticsResponse(
                        stat.getBookId(),
                        stat.getBookTitle(),
                        stat.getTotalSold()))
                .collect(Collectors.toList());
    }


}