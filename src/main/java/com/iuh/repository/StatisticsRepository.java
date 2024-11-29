package com.iuh.repository;

import com.iuh.entity.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StatisticsRepository extends JpaRepository<Statistics, String> {
    @Query(nativeQuery = true, value = """
            SELECT\s
                DATE_FORMAT(o.created_at, '%Y-%m') as month,
                COUNT(DISTINCT o.id) as total_orders,
                ROUND(SUM(od.quantity * b.import_price), 2) as total_import_cost,
                ROUND(SUM(od.quantity * od.price), 2) as total_sold_amount,
                ROUND(SUM(od.quantity * (od.price - b.import_price)), 2) as total_profit
            FROM orders o
            JOIN order_details od ON o.id = od.order_id
            JOIN books b ON od.book_id = b.id
            WHERE YEAR(o.created_at) = :year AND o.order_status = 'DELIVERED' -- delivered only
            GROUP BY DATE_FORMAT(o.created_at, '%Y-%m')
            ORDER BY month;
            """)
    List<Statistics> getMonthlyReportByYear(int year);
}