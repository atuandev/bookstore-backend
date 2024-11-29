package com.iuh.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.iuh.entity.Statistics;
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


}