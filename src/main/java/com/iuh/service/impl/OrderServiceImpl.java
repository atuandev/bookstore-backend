package com.iuh.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.iuh.dto.request.OrderCreationRequest;
import com.iuh.dto.request.OrderDetailRequest;
import com.iuh.dto.response.OrderResponse;
import com.iuh.entity.Order;
import com.iuh.entity.OrderDetail;
import com.iuh.exception.AppException;
import com.iuh.exception.ErrorCode;
import com.iuh.mapper.OrderMapper;
import com.iuh.repository.BookRepository;
import com.iuh.repository.OrderRepository;
import com.iuh.repository.UserRepository;
import com.iuh.service.OrderService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class OrderServiceImpl implements OrderService {
    OrderRepository orderRepository;
    UserRepository userRepository;
    BookRepository bookRepository;
    OrderMapper orderMapper;

	@Override
	public OrderResponse save(OrderCreationRequest request) {
		Order order = orderMapper.toOrder(request);
		order.setUser(userRepository.findById(request.getUserId()).orElseThrow(() -> {
			return new AppException(ErrorCode.USER_NOT_FOUND);
		}));
		
		List<OrderDetail> orderDetails = new ArrayList<>();
		Double total = 0.0;
		for(OrderDetailRequest odr : request.getOrderDetails()) {
            OrderDetail orderDetail = orderMapper.toOrderDetail(odr);
            orderDetail.setBook(bookRepository.findById(odr.getBookId()).orElseThrow(() -> {
                return new AppException(ErrorCode.BOOK_NOT_FOUND);
            }));
            orderDetail.setOrder(order);
            orderDetail.setPrice(orderDetail.getBook().getDiscountPrice());
            total += orderDetail.getPrice() * orderDetail.getQuantity();
            orderDetails.add(orderDetail);
            
		}
		order.setOrderDetails(orderDetails);
		order.setTotal(total);
		Order save = orderRepository.save(order);
		
		OrderResponse orderResponse = orderMapper.toOrderResponse(save);
		for(int i = 0 ; i < orderResponse.getOrderDetails().size();i++) {
			orderResponse.getOrderDetails().get(i).setBookTitle(orderDetails.get(i).getBook().getTitle());
		}
		return orderResponse;
	}

	@Override
	public void delete(String id) {
        orderRepository.deleteById(id);
	}
    
}
