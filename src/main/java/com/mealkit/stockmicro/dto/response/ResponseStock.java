package com.mealkit.stockmicro.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseStock {
    private Long id;
    private Integer count;
    private String unitOfMeasure;
    private String ingredientName;
}
