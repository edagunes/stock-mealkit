package com.mealkit.stockmicro.service;

import com.mealkit.stockmicro.dao.entity.Stock;
import com.mealkit.stockmicro.dao.repository.StockRepository;
import com.mealkit.stockmicro.dto.request.RequestStock;
import com.mealkit.stockmicro.dto.request.RequestUpdateStock;
import com.mealkit.stockmicro.dto.response.ResponseStock;
import com.mealkit.stockmicro.mapper.StockMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;

@Service
public class StockService {
    private final StockRepository stockRepository;

    @Value("${rabbitmq.exchange.name}")
    String queue;

    @Value("${rabbitmq.routing.key}")
    String routingKey;

    private static final Logger LOGGER = LoggerFactory.getLogger(StockService.class);

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public List<ResponseStock> getAllStock() {
        List<Stock> stockList = stockRepository.findAll();
        return StockMapper.INSTANCE.convertToResponseStockList(stockList);
    }

    public ResponseStock getStockById(Long id) {
        Optional<Stock> stock = stockRepository.findById(id);
        if (stock.isPresent()){
            return StockMapper.INSTANCE.convertToResponseStock(stock.get());
        }
        else {
            throw new InputMismatchException("There is no stock with this id number");
        }
    }

    public void createStock(RequestStock requestStock) {
        Stock stock = StockMapper.INSTANCE.convertToStock(requestStock);
        stockRepository.save(stock);
    }

    public void updateStock(RequestUpdateStock requestUpdateStock) {
        getStockById(requestUpdateStock.getId()); //For validation
        Stock stock = new Stock();
        stock.setId(requestUpdateStock.getId());
        stock.setCount(requestUpdateStock.getCount());

        //Stock.builder()
        //                        .id(requestUpdateStock.getId())
        //                        .ingredientId(requestUpdateStock.getIngredientId())
        //                        .count(requestUpdateStock.getCount())
        //                        .build());
        stockRepository.save(stock);
        LOGGER.info(String.format("Json message achieved -> %s", stock));
    }

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void decreaseStock(HashMap<Long, Integer> totalIngredients){
//        Long ingredientId = totalIngredients.keySet().iterator().next();
//        Integer quantity = totalIngredients.get(ingredientId);
//        RequestUpdateStock requestUpdateStock = new RequestUpdateStock();
//        requestUpdateStock.setId(ingredientId);
//        requestUpdateStock.setCount(quantity);
//        updateStock(requestUpdateStock);
        LOGGER.info(String.format("Json message achieved -> %s", totalIngredients));
        LOGGER.info(totalIngredients.toString());
        System.out.println(totalIngredients);
    }

    public void deleteStock(Long id) {
        stockRepository.deleteById(id);
    }
}
