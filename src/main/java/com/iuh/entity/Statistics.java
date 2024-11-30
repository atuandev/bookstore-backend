package com.iuh.entity;

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
    int totalOrders;
    double totalImportCost;
    double totalSoldAmount;
    double totalProfit;
}