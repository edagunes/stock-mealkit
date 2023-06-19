package com.mealkit.stockmicro;

import com.mealkit.stockmicro.dao.entity.Stock;
import com.mealkit.stockmicro.dao.repository.StockRepository;
import com.mealkit.stockmicro.dto.request.RequestStock;
import com.mealkit.stockmicro.dto.request.RequestUpdateStock;
import com.mealkit.stockmicro.dto.response.ResponseStock;
import com.mealkit.stockmicro.service.StockService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.when;

@ExtendWith(MockitoExtension.class)
public class StockServiceTest {

    StockService stockService;

    @Mock
    private StockRepository stockRepository;

    @BeforeEach
    void setup(){
        stockService = new StockService(stockRepository);
    }

    @Test
    void getAllStock(){
        List<Stock> stockList = new ArrayList<>();
        when(stockRepository.findAll()).thenReturn(stockList);

        Assertions.assertNotNull(stockService.getAllStock());
    }

    @Test
    void getStockById(){
        when(stockRepository.findById(any())).thenReturn(Optional.of(new Stock()));
        Assertions.assertNotNull(stockService.getStockById(any()));
    }

    @Test
    void getStockByIdForNull() {
        when(stockRepository.findById(any())).thenReturn(Optional.empty());

        Assertions.assertThrows(InputMismatchException.class ,() -> stockService.getStockById(any()));
    }



    @Test
    void createStock(){
        final RequestStock requestStock = new RequestStock();
        requestStock.setCount(1);

        when(stockRepository.save(any())).thenReturn(new Stock());

        stockService.createStock(requestStock);
    }

    @Test
    void updateStockTest() {
        final RequestUpdateStock requestUpdateStock = new RequestUpdateStock();
        requestUpdateStock.setId(1L);
        final Stock stock = new Stock();
        stock.setId(1L);
        stock.setCount(1);
        when(stockRepository.findById(any())).thenReturn(Optional.of(stock));

        Assertions.assertDoesNotThrow(() -> stockService.updateStock(requestUpdateStock));

    }


    @Test
    void decreaseStockTest(){
        final HashMap<Long, Integer> totalIngredients = new HashMap<>();
        totalIngredients.put(1L, 1);
        final ResponseStock responseStock = new ResponseStock();
        responseStock.setId(1L);
        responseStock.setCount(10);
        final Stock stock = new Stock();
        stock.setId(1L);
        stock.setCount(1);
        when(stockRepository.findById(any())).thenReturn(Optional.of(stock));

        Assertions.assertDoesNotThrow(() -> stockService.decreaseStock(totalIngredients));
    }

    @Test
    void decreaseStockTestForNull(){
        final HashMap<Long, Integer> totalIngredients = new HashMap<>();
        totalIngredients.put(1L, 1);
        final ResponseStock responseStock = new ResponseStock();
        responseStock.setId(1L);
        responseStock.setCount(0);
        final Stock stock = new Stock();
        stock.setId(1L);
        stock.setCount(0);
        when(stockRepository.findById(any())).thenReturn(Optional.of(stock));

        Assertions.assertThrows(Exception.class, () -> stockService.decreaseStock(totalIngredients));
    }

    @Test
    void deleteStockTest(){
        final Long stockId = 1L;

        stockService.deleteStock(stockId);

        verify(stockRepository, times(1)).deleteById(stockId);
    }




}
