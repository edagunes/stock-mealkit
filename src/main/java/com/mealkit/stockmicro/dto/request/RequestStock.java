package com.mealkit.stockmicro.dto.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestStock {
    private String ingredientName;
    private Integer count;
    private String unitOfMeasure;
}
