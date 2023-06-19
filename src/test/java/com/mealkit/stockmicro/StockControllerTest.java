package com.mealkit.stockmicro;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mealkit.stockmicro.controller.StockController;
import com.mealkit.stockmicro.dto.request.RequestStock;
import com.mealkit.stockmicro.dto.request.RequestUpdateStock;
import com.mealkit.stockmicro.service.StockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
public class StockControllerTest {

    private static final String ENDPOINT = "/api/v1/stocks";

    private static final String ENDPOINT_DELETE_STOCK = ENDPOINT + "/{id}";
    private MockMvc mvc;

    private ObjectMapper objectMapper;

    @Mock
    private StockService stockService;

    @BeforeEach
    public void setup(){
        objectMapper = new ObjectMapper();
        mvc = MockMvcBuilders
                .standaloneSetup(new StockController(stockService))
                .build();
    }

    @Test
    void getAllStockTest() throws Exception {
        mvc
                .perform(get(ENDPOINT))
                .andExpect(status().isOk());
    }

    @Test
    void getStockByIdTest() throws Exception {
        mvc
                .perform(get(ENDPOINT_DELETE_STOCK, 1))
                .andExpect(status().isOk());
    }

    @Test
    void createStockTest() throws Exception {
        final RequestStock requestStock = new RequestStock();
        mvc
                .perform(post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestStock)))
                .andExpect(status().isCreated());
    }

    @Test
    void updateStockTest() throws Exception {
        final RequestUpdateStock requestUpdateStock = new RequestUpdateStock();
        mvc
                .perform(put(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestUpdateStock)))
                .andExpect(status().isOk());

    }

    @Test
    void deleteStockTest() throws Exception {
        mvc
                .perform(delete(ENDPOINT_DELETE_STOCK, 1))
                .andExpect(status().isGone());
    }
}
