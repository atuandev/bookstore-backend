package com.iuh.service;


import com.iuh.dto.request.DiscountRequest;
import com.iuh.entity.Discount;

import java.util.List;

public interface DiscountService {
    Discount save(DiscountRequest request);

    List<Discount> findAll();

    Discount findById(String id);

    Discount update(String id, DiscountRequest request);

    void delete(String id);
}
