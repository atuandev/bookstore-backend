package com.iuh.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.iuh.dto.request.OrderCreationRequest;
import com.iuh.dto.request.OrderDetailRequest;
import com.iuh.dto.response.OrderResponse;
import com.iuh.dto.response.PageResponse;
import com.iuh.entity.Book;
import com.iuh.entity.Order;
import com.iuh.entity.OrderDetail;
import com.iuh.exception.AppException;
import com.iuh.exception.ErrorCode;
import com.iuh.mapper.OrderMapper;
import com.iuh.repository.BookRepository;
import com.iuh.repository.OrderRepository;
import com.iuh.repository.UserRepository;
import com.iuh.service.OrderService;

import jakarta.transaction.Transactional;
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
	@Transactional
	public OrderResponse save(OrderCreationRequest request) {

		Order order = orderMapper.toOrder(request);
		order.setUser(userRepository.findById(request.getUserId()).orElseThrow(() -> {
			return new AppException(ErrorCode.USER_NOT_FOUND);
		}));

		List<OrderDetail> orderDetails = new ArrayList<>();
		Double total = 0.0;
		for (OrderDetailRequest odr : request.getOrderDetails()) {
			Book curBook = bookRepository.findById(odr.getBookId()).orElseThrow(() -> {
				return new AppException(ErrorCode.BOOK_NOT_FOUND);
			});
			if (curBook.getStock() < odr.getQuantity()) {
				throw new AppException(ErrorCode.BOOK_OUT_OF_STOCK);
			}
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
		for (int i = 0; i < orderResponse.getOrderDetails().size(); i++) {
			orderResponse.getOrderDetails().get(i).setBookTitle(orderDetails.get(i).getBook().getTitle());
		}
		return orderResponse;
	}

	@Override
	public void delete(String id) {
		if (!orderRepository.existsById(id)) {
			throw new AppException(ErrorCode.ORDER_NOT_FOUND);
		}
		orderRepository.deleteById(id);
	}

	@Override
	public OrderResponse findById(String id) {
		return orderRepository.findById(id).map(orderMapper::toOrderResponse).orElseThrow(() -> {
			return new AppException(ErrorCode.ORDER_NOT_FOUND);
		});
	}

	@Override
	public OrderResponse update(String id, OrderCreationRequest request) {
		Order order = orderRepository.findById(id).orElseThrow(() -> {
			return new AppException(ErrorCode.ORDER_NOT_FOUND);
		});
		order.setUser(userRepository.findById(request.getUserId()).orElseThrow(() -> {
			return new AppException(ErrorCode.USER_NOT_FOUND);
		}));

		List<OrderDetail> orderDetails = new ArrayList<>();
		Double total = 0.0;
		for (OrderDetailRequest odr : request.getOrderDetails()) {
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
		order.setId(id);
		Order save = orderRepository.save(order);

		OrderResponse orderResponse = orderMapper.toOrderResponse(save);
		for (int i = 0; i < orderResponse.getOrderDetails().size(); i++) {
			orderResponse.getOrderDetails().get(i).setBookTitle(orderDetails.get(i).getBook().getTitle());
		}
		return orderResponse;
	}

	@Override
	public List<OrderResponse> findAll() {
		return orderRepository.findAll().stream().map(orderMapper::toOrderResponse).toList();
	}

	@Override
	public PageResponse<List<OrderResponse>> findAllWithSortBy(int pageNo, int pageSize, String sortBy) {
		int page = pageNo > 0 ? pageNo - 1 : 0;
		//
		List<Sort.Order> sorts = new ArrayList<>();
		//
		if (StringUtils.hasLength(sortBy)) {
			// Regex to match the pattern of sortBy
			// Example: name:asc
			Pattern pattern = Pattern.compile("(\\w+?)(:)(asc|desc)");
			Matcher matcher = pattern.matcher(sortBy);
			if (matcher.find()) {
				if (matcher.group(3).equalsIgnoreCase("asc")) {
					sorts.add(new Sort.Order(Sort.Direction.ASC, matcher.group(1)));
				} else {
					sorts.add(new Sort.Order(Sort.Direction.DESC, matcher.group(1)));
				}
			}
		}
		Pageable pageable = PageRequest.of(page, pageSize, Sort.by(sorts));
		Page<Order> orders = orderRepository.findAll(pageable);
		List<OrderResponse> items = orders.map(orderMapper::toOrderResponse).getContent();

		return PageResponse.<List<OrderResponse>>builder().pageNo(pageable.getPageNumber() + 1)
				.pageSize(pageable.getPageSize()).totalPages(orders.getTotalPages()).items(items).build();
		
//				return ;
	}

}
