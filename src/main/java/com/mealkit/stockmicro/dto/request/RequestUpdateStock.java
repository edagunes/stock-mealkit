package com.mealkit.stockmicro.dto.request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestUpdateStock {
    private Long id;
    private String ingredientName;
    private Integer count;
    private String unitOfMeasure;
}
