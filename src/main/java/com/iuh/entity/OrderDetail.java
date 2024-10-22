package com.iuh.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "order_details")
public class OrderDetail extends AbstractEntity {

    Integer quantity;

    Double price;

    @ManyToOne()
    @JoinColumn(name = "order_id")
    Order order;

    @ManyToOne()
    @JoinColumn(name = "book_id")
    Book book;

}
