package com.iuh.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class OrderResponse {
    String id;
    String receiverName;
    String receiverPhone;
    String address;
    String paymentMethod;
    String orderStatus;
    Double total;
    String userId;
    List<OrderDetailResponse> orderDetails;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
