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

import java.util.*;

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

    public List<ResponseStock> getAllStocksByIds(List<Long> ids){
        List<Stock> stockList = stockRepository.findAllById(ids);
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
        stock.setCount(requestStock.getCount());
        stockRepository.save(stock);
    }

    public void updateStock(RequestUpdateStock requestUpdateStock){
        getStockById(requestUpdateStock.getId());

        Stock stock = new Stock();
        stock.setId(requestUpdateStock.getId());
        stock.setCount(requestUpdateStock.getCount());
        stock.setIngredientName(requestUpdateStock.getIngredientName());
        stock.setUnitOfMeasure(requestUpdateStock.getUnitOfMeasure());

        stockRepository.save(stock);
        LOGGER.info(String.format("Json message achieved -> %s", stock));
    }

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void decreaseStock(HashMap<Long, Integer> totalIngredients) throws Exception {
        final Boolean isCovered = checkStock(totalIngredients);
        if (!isCovered){
            throw new Exception("Not Enough Stock!!");
        }
        populateStock(totalIngredients);
        LOGGER.info(String.format("Json message achieved -> %s", totalIngredients));
        LOGGER.info(totalIngredients.toString());
        System.out.println(totalIngredients);
    }

    private void populateStock(HashMap<Long, Integer> totalIngredients){
        for (Map.Entry<Long, Integer> ingredient : totalIngredients.entrySet()){
            final ResponseStock stock = getStockById(ingredient.getKey());
            final RequestUpdateStock updateStock = new RequestUpdateStock();
            updateStock.setId(stock.getId());
            updateStock.setCount(stock.getCount() - ingredient.getValue());
            updateStock.setIngredientName(stock.getIngredientName());
            updateStock.setUnitOfMeasure(stock.getUnitOfMeasure());
            updateStock(updateStock);
        }
    }

    private Boolean checkStock(HashMap<Long, Integer> totalIngredients) {
        for (Map.Entry<Long, Integer> ingredient : totalIngredients.entrySet()){
            final ResponseStock stock = getStockById(ingredient.getKey());
            if(ingredient.getValue() > stock.getCount()){
                return false;
            }
        }
        return true;
    }

    public void deleteStock(Long id) {
        stockRepository.deleteById(id);
    }
}
