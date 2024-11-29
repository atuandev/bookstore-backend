package com.iuh.service;
import com.iuh.dto.response.StatisticsResponse;

import java.util.List;

public interface StatisticsService {
    List<StatisticsResponse> getStatisticsByYear(int year);
}