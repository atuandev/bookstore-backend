package com.iuh.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "user_address")
public class UserAddress extends AbstractEntity {

    @Column(name = "receiver_name")
    String receiverName;

    @Column(name = "receiver_phone")
    String receiverPhone;

    @Column(name = "address")
    String address;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}
