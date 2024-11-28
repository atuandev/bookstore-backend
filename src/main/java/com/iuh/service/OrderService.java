package com.iuh.service;


import java.util.List;

import com.iuh.dto.request.OrderCreationRequest;
import com.iuh.dto.response.OrderResponse;
import com.iuh.dto.response.PageResponse;

public interface OrderService {
    OrderResponse save(OrderCreationRequest request);
    
    void delete(String id);
    
    OrderResponse findById(String id);
    
    OrderResponse update(String id, OrderCreationRequest request);
    
    List<OrderResponse> findAll();
    
    PageResponse<List<OrderResponse>> findAllWithSortBy(int pageNo, int pageSize, String sortBy);
}
