package com.iuh.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Setter
@Getter
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Statistics {
    @Id
    String month;
    @Column(columnDefinition = "integer default 0")
    int totalOrders =0;
    @Column(columnDefinition = "double default 0")
    double totalImportCost = 0.0;
    @Column(columnDefinition = "double default 0")
    double totalSoldAmount = 0.0;
    @Column(columnDefinition = "double default 0")
    double totalProfit = 0.0;
}