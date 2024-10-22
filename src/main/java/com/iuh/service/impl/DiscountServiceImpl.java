package com.iuh.service.impl;

import com.iuh.dto.request.DiscountRequest;
import com.iuh.entity.Discount;
import com.iuh.exception.AppException;
import com.iuh.exception.ErrorCode;
import com.iuh.mapper.DiscountMapper;
import com.iuh.repository.DiscountRepository;
import com.iuh.service.DiscountService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class DiscountServiceImpl implements DiscountService {
    DiscountRepository discountRepository;
    DiscountMapper discountMapper;

    @Override
    public Discount save(DiscountRequest request) {
        return discountRepository.save(discountMapper.toEntity(request));
    }

    @Override
    public List<Discount> findAll() {
        return discountRepository.findAll();
    }

    @Override
    public Discount findById(String id) {
        return discountRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.DISCOUNT_NOT_FOUND));
    }

    @Override
    public Discount update(String id, DiscountRequest request) {
        Discount discount = findById(id);
        discountMapper.toUpdateEntity(discount, request);
        return discountRepository.save(discount);
    }

    @Override
    public void delete(String id) {
        discountRepository.deleteById(id);
    }
}
