package com.mealkit.stockmicro.mapper;

import com.mealkit.stockmicro.dao.entity.Stock;
import com.mealkit.stockmicro.dto.request.RequestStock;
import com.mealkit.stockmicro.dto.response.ResponseStock;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StockMapper {
    StockMapper INSTANCE = Mappers.getMapper(StockMapper.class);

    ResponseStock convertToResponseStock(Stock stock);

    List<ResponseStock> convertToResponseStockList(List<Stock> stockList);

    Stock convertToStock(RequestStock requestStock);
}
