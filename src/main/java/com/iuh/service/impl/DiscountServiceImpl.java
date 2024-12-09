package com.iuh.service.impl;

import com.iuh.dto.request.DiscountRequest;
import com.iuh.dto.response.PageResponse;
import com.iuh.entity.Discount;
import com.iuh.exception.AppException;
import com.iuh.exception.ErrorCode;
import com.iuh.mapper.DiscountMapper;
import com.iuh.repository.DiscountRepository;
import com.iuh.service.DiscountService;
import com.iuh.util.PageResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        if (discountRepository.existsByCode(request.getCode())) {
            throw new AppException(ErrorCode.DISCOUNT_CODE_EXISTED);
        }
        return discountRepository.save(discountMapper.toEntity(request));
    }

    @Override
    public PageResponse<Object> findAll(int pageNo, int pageSize, String sortBy, String search) {
        int page = pageNo > 0 ? pageNo - 1 : 0;

        List<Sort.Order> sorts = PageResponseUtil.getSorts(sortBy);

        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(sorts));

        Page<Discount> discounts = discountRepository.findAllBySearch(search, pageable);

        List<Discount> items = discounts.getContent();

        return PageResponse.builder()
                .pageNo(pageable.getPageNumber() + 1)
                .pageSize(pageable.getPageSize())
                .totalPages(discounts.getTotalPages())
                .totalElements(discounts.getTotalElements())
                .items(items)
                .build();
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
