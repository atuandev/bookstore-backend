package com.iuh.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.iuh.dto.response.OrderDetailResponse;
import com.iuh.dto.response.PageResponse;
import com.iuh.entity.Book;
import com.iuh.enums.OrderStatus;
import com.iuh.repository.OrderDetailRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
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
import org.springframework.util.StringUtils;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class OrderServiceImpl implements OrderService {
    OrderRepository orderRepository;
    OrderDetailRepository orderDetailRepository;
    UserRepository userRepository;
    BookRepository bookRepository;
    OrderMapper orderMapper;

    @Override
    public OrderResponse save(OrderCreationRequest request) {
        Order order = orderMapper.toOrder(request);
        order.setUser(userRepository.findById(request.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)));

        orderRepository.save(order);

        List<OrderDetailResponse> orderDetails = new ArrayList<>();
        for (OrderDetailRequest odr : request.getOrderDetails()) {
            OrderDetail orderDetail = orderMapper.toOrderDetail(odr);

            orderDetail.setOrder(order);

            Book book = bookRepository.findById(odr.getBookId())
                    .orElseThrow(() -> new AppException(ErrorCode.BOOK_NOT_FOUND));
            boolean isOutOfStock = book.getStock() < odr.getQuantity();
            if (isOutOfStock) {
                throw new AppException(ErrorCode.BOOK_OUT_OF_STOCK);
            }
            // Update stock and sold
            book.setStock(book.getStock() - odr.getQuantity());
            book.setSold(book.getSold() + odr.getQuantity());
            bookRepository.save(book);
            orderDetail.setBook(book);

            orderDetailRepository.save(orderDetail);
            orderDetails.add(orderMapper.mapOrderDetailToResponse(orderDetail));
        }

        return OrderResponse.builder()
                .id(order.getId())
                .receiverName(order.getReceiverName())
                .receiverPhone(order.getReceiverPhone())
                .address(order.getAddress())
                .paymentMethod(order.getPaymentMethod().name())
                .orderStatus(order.getOrderStatus().name())
                .total(order.getTotal())
                .userId(order.getUser().getId())
                .orderDetails(orderDetails)
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
    }

    @Override
    public OrderResponse findById(String id) {
        return orderRepository.findById(id)
                .map(orderMapper::toOrderResponse)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public PageResponse<Object> findAllWithSortBy(int pageNo, int pageSize, String sortBy) {
        int page = pageNo > 0 ? pageNo - 1 : 0;

        List<Sort.Order> sorts = new ArrayList<>();

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

        return PageResponse.builder()
                .pageNo(pageable.getPageNumber() + 1)
                .pageSize(pageable.getPageSize())
                .totalPages(orders.getTotalPages())
                .items(items)
                .build();
    }

    @Override
    public PageResponse<Object> findAllByUserIdWithSortBy(String userId, int pageNo, int pageSize, String sortBy) {
        int page = pageNo > 0 ? pageNo - 1 : 0;

        List<Sort.Order> sorts = new ArrayList<>();

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

        Page<Order> orders = orderRepository.findAllByUser_Id(userId, pageable);

        List<OrderResponse> items = orders.map(orderMapper::toOrderResponse).getContent();

        return PageResponse.builder()
                .pageNo(pageable.getPageNumber() + 1)
                .pageSize(pageable.getPageSize())
                .totalPages(orders.getTotalPages())
                .items(items)
                .build();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void changeStatus(String id, OrderStatus status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        order.setOrderStatus(status);
        orderRepository.save(order);
    }

}
