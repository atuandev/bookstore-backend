package com.iuh.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.iuh.dto.request.OrderCreationRequest;
import com.iuh.dto.request.OrderDetailRequest;
import com.iuh.dto.response.OrderDetailReponse;
import com.iuh.dto.response.OrderResponse;
import com.iuh.entity.Order;
import com.iuh.entity.OrderDetail;

@Mapper(componentModel = "spring")
public interface OrderMapper {
	
	@Mapping(target = "total", ignore = true)
//	@Mapping(target = "phone", source = "receiverPhone")
	@Mapping(target = "user", ignore = true)
	Order toOrder(OrderCreationRequest request);
	
	@Mapping(target = "order", ignore = true)
	@Mapping(target = "book", ignore = true)
	@Mapping(target = "price", ignore = true)
	OrderDetail toOrderDetail(OrderDetailRequest request);
	
//	@Mapping(target = "receiverPhone", source = "phone")
//	@Mapping(target = "bookTitle", source = "orderDetails.book.title")
	@Mapping(source = "orderDetails", target = "orderDetails") // Map nested list
    OrderResponse toOrderResponse(Order order);
	
	@Mapping(source = "book.title", target = "bookTitle") // Explicit mapping
    OrderDetailReponse mapOrderDetailToResponse(OrderDetail orderDetail);
	
	List<OrderDetailReponse> mapOrderDetailsToResponse(List<OrderDetail> orderDetails);
//
//    @Mapping(target = "bookImages", source = "bookImages")
//    BookResponse toResponse(Book book);
//
//    @Mapping(target = "category", ignore = true)
//    @Mapping(target = "publisher", ignore = true)
//    @Mapping(target = "discount", ignore = true)
//    @Mapping(target = "bookImages", ignore = true)
//    void toUpdateEntity(@MappingTarget Book category, BookUpdateRequest request);
}
