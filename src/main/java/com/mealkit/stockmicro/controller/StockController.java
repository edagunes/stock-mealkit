package com.mealkit.stockmicro.controller;

import com.mealkit.stockmicro.dto.request.RequestStock;
import com.mealkit.stockmicro.dto.request.RequestUpdateStock;
import com.mealkit.stockmicro.dto.response.ResponseStock;
import com.mealkit.stockmicro.service.StockService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stocks")
public class StockController {
    private final StockService stockService;

    public StockController(StockService stockService){
        this.stockService = stockService;
    }

    @GetMapping
    public ResponseEntity<List<ResponseStock>> getAllStock(){
        List<ResponseStock> responseStockList = stockService.getAllStock();
        return ResponseEntity.ok(responseStockList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseStock> getStockById(@PathVariable Long id){
        ResponseStock responseStock = stockService.getStockById(id);
        return ResponseEntity.ok(responseStock);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createStock(@RequestBody RequestStock requestStock){
        stockService.createStock(requestStock);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateStock(@RequestBody RequestUpdateStock requestUpdateStock){ // increment and decrement impl
        stockService.updateStock(requestUpdateStock);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.GONE)
    public void deleteStock(@PathVariable Long id){
        stockService.deleteStock(id);
    }
}
