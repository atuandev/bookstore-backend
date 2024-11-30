package com.iuh.repository;

import com.iuh.entity.BookStatistics;
import com.iuh.entity.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    @Query(nativeQuery = true, value = """
            SELECT DATE_FORMAT(o.created_at, '%Y-%m') as month,
                COUNT(DISTINCT o.id) as total_orders,
                ROUND(SUM(od.quantity * b.import_price), 2) as total_import_cost,
                ROUND(SUM(od.quantity * od.price), 2) as total_sold_amount,
                ROUND(SUM(od.quantity * (od.price - b.import_price)), 2) as total_profit
            FROM orders o
            JOIN order_details od ON o.id = od.order_id
            JOIN books b ON od.book_id = b.id
            WHERE o.order_status <> 'CANCELLED' -- ignore cancelled order (omg pls no bom hang)
            GROUP BY DATE_FORMAT(o.created_at, '%Y-%m')
            ORDER BY month;
            """)
    List<Statistics> getAllTimeStats();

    @Query(nativeQuery = true, value = """
            SELECT b.id as bookId, b.title as bookTitle, SUM(od.quantity) as totalSold
            FROM order_details od
            JOIN books b ON od.book_id = b.id
            JOIN orders o ON od.order_id = o.id
            GROUP BY b.id, b.title
            ORDER BY totalSold DESC LIMIT :limit; -- add WHERE o.order_status = 'DELIVERED' when u only check for completed orders
            """)
    List<BookStatistics> getMostSellingBooks(int limit);
    @Query(nativeQuery = true, value = """
            SELECT b.id as bookId, b.title as bookTitle, SUM(od.quantity) as totalSold,
                MIN(CASE\s
                    WHEN DAY(o.created_at) BETWEEN 1 AND 7 THEN 1
                    WHEN DAY(o.created_at) BETWEEN 8 AND 14 THEN 2
                    WHEN DAY(o.created_at) BETWEEN 15 AND 21 THEN 3
                    WHEN DAY(o.created_at) BETWEEN 22 AND 28 THEN 4
                    ELSE 5
                END) as week
            FROM order_details od
            JOIN books b ON od.book_id = b.id
            JOIN orders o ON od.order_id = o.id
            WHERE o.order_status <> 'CANCELLED' AND MONTH(o.created_at) = :month AND YEAR(o.created_at) = :year
            GROUP BY b.id, b.title
            ORDER BY totalSold DESC;
            """)
    List<BookStatistics> getMostSellingBooksByMonth(int month, int year);
}