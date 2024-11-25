package com.iuh.service;


import com.iuh.dto.request.OrderCreationRequest;
import com.iuh.dto.response.OrderResponse;

public interface OrderService {
    OrderResponse save(OrderCreationRequest request);
    
    void delete(String id);
}
