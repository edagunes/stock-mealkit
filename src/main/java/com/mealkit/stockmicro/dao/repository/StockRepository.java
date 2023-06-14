package com.mealkit.stockmicro.dao.repository;

import com.mealkit.stockmicro.dao.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {
}
