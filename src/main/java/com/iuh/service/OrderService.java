package com.iuh.service;


import com.iuh.dto.request.OrderCreationRequest;
import com.iuh.dto.response.OrderResponse;
import com.iuh.dto.response.PageResponse;

public interface OrderService {
    OrderResponse save(OrderCreationRequest request);

    OrderResponse findById(String id);

    PageResponse<Object> findAllWithSortBy(int pageNo, int pageSize, String sortBy);

    PageResponse<Object> findAllByUserIdWithSortBy(String userId, int pageNo, int pageSize, String sortBy);
}
