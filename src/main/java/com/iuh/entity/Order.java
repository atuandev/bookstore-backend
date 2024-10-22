package com.iuh.entity;

import com.iuh.enums.OrderStatus;
import com.iuh.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "orders")
public class Order extends AbstractEntity {

    String receiverName;

    String phone;

    String address;

    @Enumerated(EnumType.STRING)
    PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    OrderStatus orderStatus = OrderStatus.PENDING;

    Double total;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    User user;

}
