package com.mealkit.stockmicro.dto.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestStock {
    private Long ingredientId;
    private Integer count;
}
