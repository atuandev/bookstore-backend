package com.iuh.mapper;

import com.iuh.dto.request.OrderCreationRequest;
import com.iuh.dto.request.OrderDetailRequest;
import com.iuh.dto.response.OrderDetailResponse;
import com.iuh.dto.response.OrderResponse;
import com.iuh.entity.Order;
import com.iuh.entity.OrderDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "orderDetails", ignore = true)
    Order toOrder(OrderCreationRequest request);

    @Mapping(target = "order", ignore = true)
    @Mapping(target = "book", ignore = true)
    OrderDetail toOrderDetail(OrderDetailRequest request);

    @Mapping(source = "book.title", target = "bookTitle")
    @Mapping(source = "book.slug", target = "slug")
    OrderDetailResponse mapOrderDetailToResponse(OrderDetail orderDetail);

    OrderResponse toOrderResponse(Order order);
}
