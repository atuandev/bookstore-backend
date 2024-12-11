package com.iuh.service.impl;

import com.iuh.dto.request.OrderCreationRequest;
import com.iuh.dto.request.OrderDetailRequest;
import com.iuh.dto.response.OrderDetailResponse;
import com.iuh.dto.response.OrderResponse;
import com.iuh.dto.response.PageResponse;
import com.iuh.entity.Book;
import com.iuh.entity.Order;
import com.iuh.entity.OrderDetail;
import com.iuh.enums.OrderStatus;
import com.iuh.exception.AppException;
import com.iuh.exception.ErrorCode;
import com.iuh.mapper.OrderMapper;
import com.iuh.repository.BookRepository;
import com.iuh.repository.OrderDetailRepository;
import com.iuh.repository.OrderRepository;
import com.iuh.repository.UserRepository;
import com.iuh.service.OrderService;
import com.iuh.util.PageUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        Order order = getOrderById(id);
        return orderMapper.toOrderResponse(order);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public PageResponse<Object> findAllOrders(int pageNo, int pageSize, String sortBy) {
        Pageable pageable = PageUtil.getPageable(pageNo, pageSize, sortBy);

        Page<Order> orders = orderRepository.findAll(pageable);

        List<OrderResponse> items = orders.map(orderMapper::toOrderResponse).getContent();

        return PageUtil.getPageResponse(pageable, orders, items);
    }

    @Override
    public PageResponse<Object> findAllByUserId(String userId, int pageNo, int pageSize, String sortBy) {
        Pageable pageable = PageUtil.getPageable(pageNo, pageSize, sortBy);

        Page<Order> orders = orderRepository.findAllByUser_Id(userId, pageable);

        List<OrderResponse> items = orders.map(orderMapper::toOrderResponse).getContent();

        return PageUtil.getPageResponse(pageable, orders, items);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void changeStatus(String id, OrderStatus status) {
        Order order = getOrderById(id);
        order.setOrderStatus(status);
        orderRepository.save(order);
    }

    private Order getOrderById(String id) {
        return orderRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
    }

}
